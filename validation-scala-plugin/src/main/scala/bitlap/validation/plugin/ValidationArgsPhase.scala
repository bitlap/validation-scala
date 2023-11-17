package bitlap.validation.plugin

import scala.annotation.threadUnsafe

import dotty.tools.dotc.ast.{ tpd, untpd }
import dotty.tools.dotc.ast.Trees.ValDef
import dotty.tools.dotc.ast.tpd.*
import dotty.tools.dotc.core.*
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Flags.*
import dotty.tools.dotc.core.Names.*
import dotty.tools.dotc.core.StdNames.nme
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
    TermsName.Validated_Annotation
  )

  @threadUnsafe private lazy val ValidBindingAnnotationClass: Context ?=> ClassSymbol = requiredClass(
    TermsName.ValidBinding_Annotation
  )

  @threadUnsafe private lazy val PreconditionsClass: Context ?=> TermSymbol = requiredModule(
    TermsName.Preconditions_Class
  )

  @threadUnsafe private lazy val ZioPreconditionsClass: Context ?=> TermSymbol = requiredModule(
    TermsName.ZioPreconditions_Class
  )

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
      tree.termParamss.flatten.find(_.tpt.symbol.showFullName == TermsName.BindingResult_Class)
    if (annotedParams.isEmpty) tree else mapDefDef(annotedParams, tree, bindingOpt)
  }

  private def mapDefDef(annotedParams: List[ValDef[Type]], tree: DefDef, bindingOpt: Option[ValDef[Type]])(using
    ctx: Context
  ): DefDef =
    val rhs    = getMethodBody(annotedParams, tree, bindingOpt)
    val newDef = DefDef(tree.symbol.asTerm, tree.termParamss.map(_.map(_.symbol)), tree.tpe, rhs)
    report.debugwarn(s"Validation updated method: ${newDef.show}")
    newDef

  private def getMethodBody(annotedParams: List[ValDef[Type]], tree: DefDef, bindingOpt: Option[ValDef[Type]])(using
    ctx: Context
  ) = {
    // ignore if user add @ValidBinding on BindingResult
    val params = annotedParams
      .filterNot(_.tpt.symbol.showFullName == TermsName.BindingResult_Class)
      .map(a => untpd.Ident(a.name).withType(a.tpe))

    val input      = List(mkList(params, TypeTree(defn.AnyType, false)))
    val typeParams = tree.tpt.symbol.typeParams.map(_.show)
    if (tree.tpt.symbol.showFullName == TermsName.Zio_Class && typeParams.size == 3) {
      report.debugwarn(s"Validation apply zio method: ${tree.name.show}")
      val body = bindingOpt.fold {
        ref(ZioPreconditionsClass.requiredMethod(TermsName.validateObject_Method))
          .withSpan(ctx.owner.span.focus)
          .appliedToArgs(List(tree.rhs))
          .appliedToArgs(input)
      } { binding =>
        val bindTerm = untpd.Ident(binding.name).withType(binding.tpe)
        ref(ZioPreconditionsClass.requiredMethod(TermsName.validateObjectBinding_Method))
          .withSpan(ctx.owner.span.focus)
          .appliedToArgs(List(bindTerm))
          .appliedToArgs(List(tree.rhs))
          .appliedToArgs(input)
      }

      Block(
        List.empty,
        body
      )
    } else {
      report.debugwarn(s"Validation apply normal method: ${tree.name.show}")
      val body = bindingOpt.fold {
        ref(PreconditionsClass.requiredMethod(TermsName.validateObject_Method))
          .withSpan(ctx.owner.span.focus)
          .appliedToArgs(input)
      } { binding =>
        val bindTerm = untpd.Ident(binding.name).withType(binding.tpe)
        ref(PreconditionsClass.requiredMethod(TermsName.validateObjectBinding_Method))
          .withSpan(ctx.owner.span.focus)
          .appliedToArgs(List(bindTerm))
          .appliedToArgs(input)
      }
      Block(
        List(body),
        tree.rhs
      )
    }
  }
