package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Min

import bitlap.validation.Utils._
import bitlap.validation.function.MinFunction

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the number being validated represents a number,
 * and has a value more than or equal to the minimum value specified.
 */
class MinValidatorForOption extends ConstraintValidator[Min, IterableOnce[_]] {
  private var constraintAnnotation: Min = _

  override def initialize(constraintAnnotation: Min): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new MinFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
