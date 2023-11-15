package bitlap.validation.plugin

import scala.annotation.threadUnsafe

import dotty.tools.dotc.ast.{ tpd, untpd }
import dotty.tools.dotc.ast.Trees.ValDef
import dotty.tools.dotc.ast.tpd.*
import dotty.tools.dotc.core.*
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Flags.*
import dotty.tools.dotc.core.Names.*
import dotty.tools.dotc.core.Symbols.*
import dotty.tools.dotc.core.Types.*
import dotty.tools.dotc.plugins.PluginPhase
import dotty.tools.dotc.report
import dotty.tools.dotc.transform.*

final class ValidationArgsPhase extends PluginPhase:

  override val phaseName: String       = ValidationCompilerPlugin.name
  override val description: String     = ValidationCompilerPlugin.description
  override val runsAfter: Set[String]  = Set(Staging.name)
  override val runsBefore: Set[String] = Set(PickleQuotes.name)

  @threadUnsafe private lazy val ValidatedAnnotationClass: Context ?=> ClassSymbol = requiredClass(
    "bitlap.validation.ext.Validated"
  )

  @threadUnsafe private lazy val ValidBindingAnnotationClass: Context ?=> ClassSymbol = requiredClass(
    "bitlap.validation.ext.ValidBinding"
  )

  @threadUnsafe private lazy val RuntimeClass: Context ?=> TermSymbol = requiredModule(
    "bitlap.validation.ext.Preconditions"
  )

  private val bindingClassName = "bitlap.validation.ext.BindingResult"

  override def transformDefDef(tree: DefDef)(using Context): Tree = {
    val annotats      = tree.termParamss.flatten.map(p => p -> p.mods.annotations)
    val annotedParams = annotats.map { case (field, ans) =>
      val existAnnot = ans.collect {
        case a @ Apply(Select(New(Ident(an)), _), Nil)
            if an.asSimpleName == ValidatedAnnotationClass.name.asSimpleName ||
              an.asSimpleName == ValidBindingAnnotationClass.name.asSimpleName =>
          report.debugwarn(s"Validation found param: ${field.name.show} in ${tree.name.show}")
          a
      }
      if (existAnnot.nonEmpty) Some(field) else None
    }.collect { case Some(value) =>
      value
    }
    val bindingOpt    =
      tree.termParamss.flatten.filter(_.tpt.symbol.showFullName == bindingClassName).headOption
    if (annotedParams.isEmpty) tree else mapDefDef(annotedParams, tree, bindingOpt)
  }

  private def mapDefDef(annotedParams: List[ValDef[Type]], tree: DefDef, bindingOpt: Option[ValDef[Type]])(using
    ctx: Context
  ): DefDef =
    // ignore if user add @ValidBinding on BindingResult
    val params = annotedParams
      .filterNot(_.tpt.symbol.fullName.asTypeName.toString == bindingClassName)
      .map(a => untpd.Ident(a.name).withType(a.tpe))

    val body = bindingOpt.fold {
      ref(RuntimeClass.requiredMethod("validateArgs"))
        .withSpan(ctx.owner.span.focus)
        .appliedToVarargs(params, TypeTree(defn.AnyType, false))
    } { binding =>
      val bindTerm = untpd.Ident(binding.name).withType(binding.tpe)
      ref(RuntimeClass.requiredMethod("validateArgsBinding"))
        .withSpan(ctx.owner.span.focus)
        .appliedToArgs(List(bindTerm))
        .appliedToVarargs(params, TypeTree(defn.AnyType, false))
    }

    val rhs    = Block(
      List(body),
      tree.rhs
    )
    val newDef = DefDef(tree.symbol.asTerm, tree.termParamss.map(_.map(_.symbol)), tree.tpe, rhs)
    report.debugwarn(s"Validation updated method: ${newDef.show}")
    newDef
