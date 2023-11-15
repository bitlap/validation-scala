package bitlap.validation

import org.hibernate.validator.constraints.Length

import bitlap.validation.Utils._
import bitlap.validation.function.LengthFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Check that the wrapped character sequence length is between min and max.
 */
class LengthValidatorForOption extends ConstraintValidator[Length, IterableOnce[_]] {
  private var function: LengthFunction = _

  override def initialize(constraintAnnotation: Length): Unit =
    function = LengthFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
