package bitlap.validation

import bitlap.validation.function.NegativeOrZeroFunction
import jakarta.validation.constraints.NegativeOrZero
import jakarta.validation.{ConstraintValidator, ConstraintValidatorContext}

/**
 * Validates that the wrapped value passed is a negative number or zero.
 */
class NegativeOrZeroValidatorForOption extends ConstraintValidator[NegativeOrZero, Option[_]] {

  private var function: NegativeOrZeroFunction = _

  override def initialize(constraintAnnotation: NegativeOrZero): Unit =
    function = new NegativeOrZeroFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)

}
