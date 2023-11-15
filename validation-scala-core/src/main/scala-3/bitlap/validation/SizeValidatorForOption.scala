package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.SizeFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Size

/**
 * Check that the length of a wrapped value is between min and max.
 */
class SizeValidatorForOption extends ConstraintValidator[Size, IterableOnce[_]] {
  private var function: SizeFunction = _

  override def initialize(constraintAnnotation: Size): Unit =
    function = SizeFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))

}
