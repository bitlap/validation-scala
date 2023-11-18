package bitlap.validation.plugin

import scala.annotation.threadUnsafe

import dotty.tools.dotc.ast.{ tpd, untpd }
import dotty.tools.dotc.ast.Trees.ValDef
import dotty.tools.dotc.ast.tpd.*
import dotty.tools.dotc.cc.CaptureAnnotation
import dotty.tools.dotc.core.*
import dotty.tools.dotc.core.Constants.Constant
import dotty.tools.dotc.core.Contexts.Context
import dotty.tools.dotc.core.Flags.*
import dotty.tools.dotc.core.Names.*
import dotty.tools.dotc.core.StdNames.nme
import dotty.tools.dotc.core.Symbols.*
import dotty.tools.dotc.core.Types.{ Type, * }
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

  @threadUnsafe private lazy val MethodIdentityClass: Context ?=> TermSymbol = requiredModule(
    TermsName.MethodIdentity_Class
  )

  private inline def const(any: Any): Context ?=> tpd.Tree = Literal(Constant(any))

  override def transformDefDef(tree: DefDef)(using Context): Tree = {
    // only public methods of classes are supported
    if (!tree.symbol.owner.isClass || tree.symbol.isStatic || tree.symbol.isPrivate) {
      return tree
    }

    // to determine if there are main scala annotations
    val optAnnotations: Option[(Boolean, ClassSymbol)] = tree.symbol.annotations.collectFirst {
      case annotation if annotation.symbol.name.asSimpleName == ValidatedAnnotationClass.name.asSimpleName    =>
        report.debugwarn(s"Validation found: ${TermsName.Validated_Annotation} on method: ${tree.name.show}")
        false -> ValidatedAnnotationClass
      case annotation if annotation.symbol.name.asSimpleName == ValidBindingAnnotationClass.name.asSimpleName =>
        report.debugwarn(s"Validation found binding: ${TermsName.ValidBinding_Annotation} on method: ${tree.name.show}")
        true -> ValidBindingAnnotationClass
    }
    if optAnnotations.nonEmpty then return tree

    val bindOpt =
      if (optAnnotations.exists(_._1))
        tree.termParamss.flatten.find(_.tpt.symbol.showFullName == TermsName.BindingResult_Class)
      else None
    mapDefDef(tree, bindOpt)
  }

  private def mapDefDef(tree: DefDef, bindingOpt: Option[ValDef[Type]]): Context ?=> DefDef =
    val rhs    = getMethodBody(tree, bindingOpt)
    val newDef = DefDef(tree.symbol.asTerm, tree.termParamss.map(_.map(_.symbol)), tree.tpe, rhs)
    report.debugwarn(s"Validation updated method: ${newDef.show}")
    newDef

  private def getMethodBody(tree: DefDef, bindingOpt: Option[ValDef[Type]]): Context ?=> Block = {
    // ignore if user add @ValidBinding on BindingResult
    val params          = tree.termParamss.flatten.map(a => untpd.Ident(a.name).withType(a.tpe))
    val obj             = This(tree.symbol.owner.asClass)
    val method          =
      ref(MethodIdentityClass).select(nme.apply).appliedToArgs(List(const(tree.name.show), const(params.size)))
    val parameterValues = mkList(params, TypeTree(defn.AnyType, false))
    val input           = List(obj, method, parameterValues)

    val typeParams = tree.tpt.symbol.typeParams.map(_.show)
    if (tree.tpt.symbol.showFullName == TermsName.Zio_Class && typeParams.size == 3) {
      report.debugwarn(s"Validation apply zio method: ${tree.name.show}")
      val body = bindingOpt.fold {
        ref(ZioPreconditionsClass.requiredMethod(TermsName.ValidateMethodArgs_Method))
          .withSpan(summon[Context].owner.span.focus)
          .appliedToArgs(List(tree.rhs))
          .appliedToArgs(input)
      } { binding =>
        ref(ZioPreconditionsClass.requiredMethod(TermsName.ValidateMethodArgsBinding_Method))
          .withSpan(summon[Context].owner.span.focus)
          .appliedToArgs(List(untpd.Ident(binding.name).withType(binding.tpe)))
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
        ref(PreconditionsClass.requiredMethod(TermsName.ValidateMethodArgs_Method))
          .withSpan(summon[Context].owner.span.focus)
          .appliedToArgs(input)
      } { binding =>
        ref(PreconditionsClass.requiredMethod(TermsName.ValidateMethodArgsBinding_Method))
          .withSpan(summon[Context].owner.span.focus)
          .appliedToArgs(List(untpd.Ident(binding.name).withType(binding.tpe)))
          .appliedToArgs(input)
      }
      Block(
        List(body),
        tree.rhs
      )
    }
  }
