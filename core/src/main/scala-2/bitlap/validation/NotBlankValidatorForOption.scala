package bitlap.validation

import bitlap.validation.function.NotBlankFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.NotBlank

/**
 * Check that a wrapped character sequence's (e.g. Option[String]) trimmed length is not empty.
 */
class NotBlankValidatorForOption extends ConstraintValidator[NotBlank, Option[_]] {
  private var function: NotBlankFunction = _

  override def initialize(constraintAnnotation: NotBlank): Unit =
    function = new NotBlankFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
