package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.Length

import bitlap.validation.Utils._
import bitlap.validation.function.LengthFunction

/**
 * Check that the wrapped character sequence length is between min and max.
 */
class LengthValidatorForOption extends ConstraintValidator[Length, IterableOnce[_]] {
  private var constraintAnnotation: Length = _

  override def initialize(constraintAnnotation: Length): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new LengthFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
