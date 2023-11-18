package bitlap.validation.extension

import scala.annotation.unused

object Preconditions {

  @unused
  def validateMethodArgsBinding(
    bind: BindingResult
  )(obj: Any, method: MethodIdentity, parameterValues: List[AnyRef]): BindingResult = {
    val findMethod = method.getMethod(obj)
    val violations = Validator.checkMethodArgsBinding(obj, findMethod, parameterValues.toArray)
    bind.hasErrors = violations.nonEmpty
    bind.violations.addAll(violations)
    bind
  }

  @unused
  def validateMethodArgs(obj: Any, method: MethodIdentity, parameterValues: List[AnyRef]): Unit = {
    val findMethod = method.getMethod(obj)
    Validator.checkMethodArgs(obj, findMethod, parameterValues.toArray)
  }
}
