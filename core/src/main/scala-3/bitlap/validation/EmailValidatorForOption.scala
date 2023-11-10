package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.Email

import bitlap.validation.Utils._
import bitlap.validation.function.EmailFunction

/**
 * Validates that the wrapped character sequence (e.g. Option[String]) being validated consists of digits, and matches
 * the pattern defined in the constraint.
 */
class EmailValidatorForOption extends ConstraintValidator[Email, IterableOnce[_]] {
  private var constraintAnnotation: Email = _

  override def initialize(constraintAnnotation: Email): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new EmailFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
