package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.DigitsFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Digits

/**
 * Validates that the wrapped character sequence (e.g. Option[String]) being validated consists of digits.
 */
class DigitsValidatorForOption extends ConstraintValidator[Digits, IterableOnce[_]] {
  private var function: DigitsFunction = _

  override def initialize(constraintAnnotation: Digits): Unit =
    function = new DigitsFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
