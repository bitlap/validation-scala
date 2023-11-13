package bitlap.validation

import bitlap.validation.function.{ PatternFunction, PositiveFunction }
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Positive

/**
 * Validates that the wrapped value passed is a strictly positive number.
 */
class PositiveValidatorForOption extends ConstraintValidator[Positive, Option[_]] {

  private var function: PositiveFunction = _

  override def initialize(constraintAnnotation: Positive): Unit =
    function = new PositiveFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)

}
