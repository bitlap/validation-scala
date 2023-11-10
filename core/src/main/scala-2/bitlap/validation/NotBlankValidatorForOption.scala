package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.NotBlank

import bitlap.validation.function.NotBlankFunction

/**
 * Check that a wrapped character sequence's (e.g. Option[String]) trimmed length is not empty.
 */
class NotBlankValidatorForOption extends ConstraintValidator[NotBlank, Option[_]] {
  private var constraintAnnotation: NotBlank = _

  override def initialize(constraintAnnotation: NotBlank): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new NotBlankFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
