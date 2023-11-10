package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.DecimalMax

import bitlap.validation.Utils._
import bitlap.validation.function.DecimalMaxFunction

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the wrapped number being validated represents a
 * number, and has a value less than or equal to the maximum value specified.
 */
class DecimalMaxValidatorForOption extends ConstraintValidator[DecimalMax, IterableOnce[_]] {
  private var constraintAnnotation: DecimalMax = _

  override def initialize(constraintAnnotation: DecimalMax): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new DecimalMaxFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
