package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.Length

import bitlap.validation.function.LengthFunction

/**
 * Check that the wrapped character sequence length is between min and max.
 */
class LengthValidatorForOption extends ConstraintValidator[Length, Option[_]] {
  private var constraintAnnotation: Length = _

  override def initialize(constraintAnnotation: Length): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new LengthFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
