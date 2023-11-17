package bitlap.validation

import scala.annotation.meta.field
import scala.util.control.NonFatal

import org.specs2.mutable.Specification

import bitlap.validation.extension.{ BindingResult, Preconditions }
import jakarta.validation.constraints.NotBlank

class ExtensionPreConditionSpec extends Specification {

  case class TestBean(
    @(NotBlank @field)
    hasSomething: Option[String]
  )

  def catchError(b: TestBean): Option[TestBean] = {
    try
      Preconditions.validateObject(List(b))
    catch {
      case NonFatal(e) =>
        return None
    }
    Option(b)
  }

  def bindError(b: TestBean, bean: BindingResult = BindingResult.default): Boolean =
    Preconditions.validateObjectBinding(bean)(List(b)).hasErrors

  s"Check Preconditions" >> {
    catchError(TestBean(Option("1"))).must_==(Some(TestBean(Option("1"))))
    catchError(TestBean(Option(""))).must_==(None)
    bindError(TestBean(Option("1"))).must_===(false)
    bindError(TestBean(Option(""))).must_==(true)
  }
}
