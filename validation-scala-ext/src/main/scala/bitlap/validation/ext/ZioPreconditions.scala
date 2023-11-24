package bitlap.validation.ext

import scala.annotation.unused

import bitlap.validation.extension._
import zio._

@unused
object ZioPreconditions {

  @unused
  def validateMethodArgs(
    action: ZIO[Any, Any, Any]
  )(obj: Any, method: MethodIdentity, parameterValues: List[AnyRef]): ZIO[Any, Any, Any] = {
    val findMethod = method.getMethod(obj)
    ZioValidator.checkMethodArgs(obj, findMethod, parameterValues.toArray) *> action
  }

  @unused
  def validateMethodArgsBinding(bind: BindingResult)(action: ZIO[Any, Any, Any])(
    obj: Any,
    method: MethodIdentity,
    parameterValues: List[AnyRef]
  ): ZIO[Any, Any, Any] = {
    val check = for {
      bindRef    <- Ref.make(bind)
      m          <- ZIO.attempt(method.getMethod(obj))
      violations <- ZioValidator.checkMethodArgsBinding(obj, m, parameterValues.toArray)
      _          <- bindRef.set {
                      bind.hasErrors = violations.nonEmpty
                      bind.violations.addAll(violations)
                      bind
                    }
    } yield bind

    check *> action
  }

}
