package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator

import bitlap.validation.Utils._

/**
 * Validates that the wrapped character sequence (e.g. Option[String]) being validated consists of digits, and matches
 * the pattern defined in the constraint.
 */
class EmailValidatorForOption extends ConstraintValidator[Email, IterableOnce[_]] {
  private var constraintAnnotation: Email = _

  override def initialize(constraintAnnotation: Email): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: CharSequence) =>
        val v = new EmailValidator
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}