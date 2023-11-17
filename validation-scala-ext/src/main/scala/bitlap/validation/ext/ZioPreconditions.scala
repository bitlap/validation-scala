package bitlap.validation.ext

import bitlap.validation.extension.BindingResult
import zio._

object ZioPreconditions {

  def validateObject(action: ZIO[Any, Any, Any])(objs: List[Any]): ZIO[Any, Any, Any] =
    ZIO.foreachDiscard(objs)(o => ZioValidator.checkObject(o)) *> action

  def validateObjectBinding(bind: BindingResult)(action: ZIO[Any, Any, Any])(
    objs: List[Any]
  ): ZIO[Any, Any, Any] = {
    val check = for {
      bindRef    <- Ref.make(bind)
      violations <- ZIO.foreach(objs)(o => ZioValidator.checkObjectBinding(o)).map(_.flatten)
      _          <- bindRef.set {
                      bind.hasErrors = violations.nonEmpty
                      bind.violations.addAll(violations)
                      bind
                    }
      bind       <- bindRef.get
    } yield bind

    check *> action
  }

}
