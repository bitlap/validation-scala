package bitlap.validation

import bitlap.validation.Utils.checkForOption
import bitlap.validation.function.NegativeOrZeroFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.NegativeOrZero

/**
 * Validates that the wrapped value passed is a negative number or zero.
 */
class NegativeOrZeroValidatorForOption extends ConstraintValidator[NegativeOrZero, IterableOnce[_]] {

  private var function: NegativeOrZeroFunction = _

  override def initialize(constraintAnnotation: NegativeOrZero): Unit =
    function = NegativeOrZeroFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))

}
