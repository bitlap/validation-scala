package bitlap.validation

import org.hibernate.validator.constraints.Length

import bitlap.validation.function.LengthFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Check that the wrapped character sequence length is between min and max.
 */
class LengthValidatorForOption extends ConstraintValidator[Length, Option[_]] {
  private var function: LengthFunction = _

  override def initialize(constraintAnnotation: Length): Unit =
    function = new LengthFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
