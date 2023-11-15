package bitlap.validation.ext

import bitlap.validation.Identity

object Preconditions {

  def validateArgs(objs: Any*): Identity[Unit] =
    objs.foreach(o => Validator.checkArgs(o))

  def validateArgsBinding(bind: BindingResult)(objs: Any*): BindingResult = {
    val violations = objs.flatMap(o => Validator.checkArgsBinding(o))
    bind.hasErrors = violations.nonEmpty
    bind.violations.addAll(violations)
    bind
  }
}
