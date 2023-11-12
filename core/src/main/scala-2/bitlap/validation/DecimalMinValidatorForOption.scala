package bitlap.validation

import bitlap.validation.function.DecimalMinFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.DecimalMin

/**
 * Check that the wrapped character sequence (e.g. Option[String]). The annotated element must be a number whose value
 * must be higher or equal to the specified minimum.
 */
class DecimalMinValidatorForOption extends ConstraintValidator[DecimalMin, Option[_]] {
  private var function: DecimalMinFunction = _

  override def initialize(constraintAnnotation: DecimalMin): Unit =
    function = new DecimalMinFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
