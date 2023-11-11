package bitlap.validation

import bitlap.validation.function.MinFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Min

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the number being validated represents a number,
 * and has a value more than or equal to the minimum value specified.
 */
class MinValidatorForOption extends ConstraintValidator[Min, Option[_]] {
  private var function: MinFunction = _

  override def initialize(constraintAnnotation: Min): Unit =
    function = new MinFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
