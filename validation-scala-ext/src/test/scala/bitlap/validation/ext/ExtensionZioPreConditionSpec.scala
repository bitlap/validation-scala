package bitlap.validation.ext

import scala.annotation.meta.field

import org.specs2.mutable.Specification

import bitlap.validation.extension.BindingResult
import jakarta.validation.constraints.NotBlank
import zio._

class ExtensionZioPreConditionSpec extends Specification {

  def unsafeRun(a: ZIO[Any, Any, Any]): Any = Unsafe.unsafe { implicit runtime =>
    zio.Runtime.default.unsafe.run(a).getOrThrowFiberFailure()
  }

  case class TestBean(
    @(NotBlank @field)
    hasSomething: Option[String]
  )

  def catchError(b: TestBean): ZIO[Any, Any, Any] =
    ZioPreconditions.validateArgs(ZIO.succeed(b))(List(b)).isFailure

  def bindError(b: TestBean, bean: BindingResult = BindingResult.default): ZIO[Any, Any, Any] =
    ZioPreconditions.validateArgsBinding(bean)({
      ZIO.succeed(b) *> ZIO.attempt(bean.hasErrors)
    })(List(b))

  s"Check ZioPreconditions" >> {
    unsafeRun(catchError(TestBean(Option("1")))) mustEqual false
    unsafeRun(catchError(TestBean(Option("")))) mustEqual true
    unsafeRun(bindError(TestBean(Option("1")))) mustEqual false
    unsafeRun(bindError(TestBean(Option("")))) mustEqual true
  }

}
