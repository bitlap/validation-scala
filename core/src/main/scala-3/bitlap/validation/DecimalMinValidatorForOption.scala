package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.DecimalMin

import bitlap.validation.Utils._
import bitlap.validation.function.DecimalMinFunction

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the wrapped number being validated is less than
 * or equal to the maximum value specified.
 */
class DecimalMinValidatorForOption extends ConstraintValidator[DecimalMin, IterableOnce[_]] {
  private var constraintAnnotation: DecimalMin = _

  override def initialize(constraintAnnotation: DecimalMin): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new DecimalMinFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
