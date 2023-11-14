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

final class ValidationCheckArgumnetPhase extends PluginPhase:

  override val phaseName: String       = ValidationCompilerPlugin.name
  override val description: String     = ValidationCompilerPlugin.description
  override val runsAfter: Set[String]  = Set(Staging.name)
  override val runsBefore: Set[String] = Set(PickleQuotes.name)

  @threadUnsafe private lazy val AnnotationClass: Context ?=> ClassSymbol = requiredClass(
    "bitlap.validation.ext.checkArgument"
  )

  @threadUnsafe private lazy val RuntimeClass: Context ?=> TermSymbol = requiredModule(
    "bitlap.validation.ext.ValidationRuntimeUtil"
  )

  override def transformDefDef(tree: DefDef)(using Context): Tree = {
    val annotats      = tree.termParamss.flatten.map(p => p -> p.mods.annotations)
    val annotedParams = annotats.map { case (field, ans) =>
      val existAnnot = ans.collect {
        case a @ Apply(Select(New(Ident(an)), _), Nil) if an.asSimpleName == AnnotationClass.name.asSimpleName =>
          report.debugwarn(
            s"Validation found param: ${field.name.show} in ${tree.name.show}")
          a
      }
      if (existAnnot.nonEmpty) Some(field) else None
    }.collect { case Some(value) =>
      value
    }
    if (annotedParams.isEmpty) tree else mapDefDef(annotedParams, tree)
  }

  private def mapDefDef(annotedParams: List[ValDef[Type]], tree: DefDef)(using ctx: Context): DefDef =

    val params = annotedParams.map(a => untpd.Ident(a.name).withType(a.tpe))

    val body = ref(RuntimeClass.requiredMethod("checkArgument"))
      .withSpan(ctx.owner.span.focus)
      .appliedToVarargs(params, TypeTree(defn.AnyType, false))

    val rhs    = Block(
      List(body),
      tree.rhs
    )
    val newDef = DefDef(tree.symbol.asTerm, tree.termParamss.map(_.map(_.symbol)), tree.tpe, rhs)
    report.debugwarn(
      s"Validation updated method: ${newDef.show}")
    newDef
