package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Digits

import bitlap.validation.function.DigitsFunction

/**
 * Validates that the wrapped character sequence (e.g. Option[String]) being validated consists of digits, and matches
 * the pattern defined in the constraint.
 */
class DigitsValidatorForOption extends ConstraintValidator[Digits, Option[_]] {
  private var constraintAnnotation: Digits = _

  override def initialize(constraintAnnotation: Digits): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new DigitsFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
