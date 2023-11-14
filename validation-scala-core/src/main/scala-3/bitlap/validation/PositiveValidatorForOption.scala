package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.PositiveFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Positive

/**
 * Validates that the wrapped value passed is a strictly positive number.
 */
class PositiveValidatorForOption extends ConstraintValidator[Positive, IterableOnce[_]] {

  private var function: PositiveFunction = _

  override def initialize(constraintAnnotation: Positive): Unit =
    function = new PositiveFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))

}
