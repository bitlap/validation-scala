package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.MinFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Min

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the number being validated represents a number,
 * and has a value more than or equal to the minimum value specified.
 */
class MinValidatorForOption extends ConstraintValidator[Min, IterableOnce[_]] {
  private var function: MinFunction = _

  override def initialize(constraintAnnotation: Min): Unit =
    function = MinFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
