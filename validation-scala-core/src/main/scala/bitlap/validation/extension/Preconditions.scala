package bitlap.validation.extension

object Preconditions {

  def validateArgs(objs: List[Any]): Unit =
    objs.foreach(o => Validator.checkArgs(o))

  def validateArgsBinding(bind: BindingResult)(objs: List[Any]): BindingResult = {
    val violations = objs.flatMap(o => Validator.checkArgsBinding(o))
    bind.hasErrors = violations.nonEmpty
    bind.violations.addAll(violations)
    bind
  }
}
