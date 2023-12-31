package bitlap.validation

import bitlap.validation.function.MaxFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Max

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the number being validated represents a number,
 * and has a value less than or equal to the maximum value specified.
 */
class MaxValidatorForOption extends ConstraintValidator[Max, Option[_]] {
  private var function: MaxFunction = _

  override def initialize(constraintAnnotation: Max): Unit =
    function = new MaxFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
