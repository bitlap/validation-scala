package bitlap.validation

import bitlap.validation.function.SizeFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Size

/**
 * Check that the length of a wrapped value is between min and max.
 */
class SizeValidatorForOption extends ConstraintValidator[Size, Option[_]] {
  private var function: SizeFunction = _

  override def initialize(constraintAnnotation: Size): Unit =
    function = new SizeFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
