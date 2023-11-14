package bitlap.validation

import org.hibernate.validator.constraints.URL

import bitlap.validation.function.URLFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Validate that the wrapped character sequence (e.g. Option[String]) is a valid URL.
 */
class URLValidatorForOption extends ConstraintValidator[URL, Option[_]] {
  private var function: URLFunction = _

  override def initialize(constraintAnnotation: URL): Unit =
    function = new URLFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
