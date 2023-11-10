package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Digits

import org.hibernate.validator.internal.constraintvalidators.bv.{
  DigitsValidatorForCharSequence,
  DigitsValidatorForNumber
}

import bitlap.validation.Utils._

/**
 * Validates that the wrapped character sequence (e.g. Option[String]) being validated consists of digits, and matches
 * the pattern defined in the constraint.
 */
class DigitsValidatorForOption extends ConstraintValidator[Digits, IterableOnce[_]] {
  private var constraintAnnotation: Digits = _

  override def initialize(constraintAnnotation: Digits): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: CharSequence) =>
        val v = new DigitsValidatorForCharSequence
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Number)       =>
        val v = new DigitsValidatorForNumber
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
