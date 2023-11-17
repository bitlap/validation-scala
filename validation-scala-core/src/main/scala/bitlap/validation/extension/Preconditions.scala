package bitlap.validation.extension

object Preconditions {

  def validateObject(objs: List[Any]): Unit =
    objs.foreach(o => Validator.checkObject(o))

  def validateObjectBinding(bind: BindingResult)(objs: List[Any]): BindingResult = {
    val violations = objs.flatMap(o => Validator.checkObjectBinding(o))
    bind.hasErrors = violations.nonEmpty
    bind.violations.addAll(violations)
    bind
  }
}
