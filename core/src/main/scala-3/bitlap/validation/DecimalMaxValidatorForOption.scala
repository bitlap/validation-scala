package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.DecimalMaxFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.DecimalMax

/**
 * Check that the wrapped value (e.g. Option[String]). The element must be a number whose value must be lower or equal
 * to the specified maximum.
 */
class DecimalMaxValidatorForOption extends ConstraintValidator[DecimalMax, IterableOnce[_]] {
  private var function: DecimalMaxFunction = _

  override def initialize(constraintAnnotation: DecimalMax): Unit =
    function = new DecimalMaxFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
