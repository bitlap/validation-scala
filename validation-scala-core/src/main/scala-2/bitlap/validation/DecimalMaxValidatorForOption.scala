package bitlap.validation

import bitlap.validation.function.DecimalMaxFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.DecimalMax

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the number being validated represents a number,
 * and has a value less than or equal to the maximum value specified.
 */
class DecimalMaxValidatorForOption extends ConstraintValidator[DecimalMax, Option[_]] {
  private var function: DecimalMaxFunction = _

  override def initialize(constraintAnnotation: DecimalMax): Unit =
    function = new DecimalMaxFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
