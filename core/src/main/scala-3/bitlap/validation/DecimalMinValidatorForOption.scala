package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.DecimalMinFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.DecimalMin

/**
 * Check that the wrapped value (e.g. Option[String]). The element must be a number whose value must be higher or equal
 * to the specified minimum.
 */
class DecimalMinValidatorForOption extends ConstraintValidator[DecimalMin, IterableOnce[_]] {
  private var function: DecimalMinFunction = _

  override def initialize(constraintAnnotation: DecimalMin): Unit =
    function = new DecimalMinFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
