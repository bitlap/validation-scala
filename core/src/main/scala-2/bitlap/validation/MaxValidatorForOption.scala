package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Max

import bitlap.validation.function.MaxFunction

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the number validated represents a number, and has
 * a value less than or equal to the maximum value specified.
 */
class MaxValidatorForOption extends ConstraintValidator[Max, Option[_]] {
  private var constraintAnnotation: Max = _

  override def initialize(constraintAnnotation: Max): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new MaxFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
