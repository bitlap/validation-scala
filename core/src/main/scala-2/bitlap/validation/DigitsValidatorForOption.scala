package bitlap.validation

import bitlap.validation.function.DigitsFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Digits

/**
 * Validates that the wrapped character sequence (e.g. Option[String]) being validated consists of digits, and matches
 * the pattern defined in the constraint.
 */
class DigitsValidatorForOption extends ConstraintValidator[Digits, Option[_]] {
  private var function: DigitsFunction = _

  override def initialize(constraintAnnotation: Digits): Unit =
    function = new DigitsFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
