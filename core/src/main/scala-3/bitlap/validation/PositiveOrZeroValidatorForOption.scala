package bitlap.validation

import bitlap.validation.Utils.checkForOption
import bitlap.validation.function.PositiveOrZeroFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.PositiveOrZero

/**
 * Validates that the wrapped value passed is a positive number or zero.
 */
class PositiveOrZeroValidatorForOption extends ConstraintValidator[PositiveOrZero, IterableOnce[_]] {

  private var function: PositiveOrZeroFunction = _

  override def initialize(constraintAnnotation: PositiveOrZero): Unit =
    function = new PositiveOrZeroFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))

}
