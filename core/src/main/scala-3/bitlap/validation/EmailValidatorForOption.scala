package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.EmailFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Email

/**
 * Validates that the wrapped character sequence (e.g. Option[String]) being validated consists of digits, and matches
 * the pattern defined in the constraint.
 */
class EmailValidatorForOption extends ConstraintValidator[Email, IterableOnce[_]] {
  private var function: EmailFunction = _

  override def initialize(constraintAnnotation: Email): Unit =
    function = new EmailFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
