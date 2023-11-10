package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Past

import bitlap.validation.function.TimePastFunction

/**
 * Check that the wrapped Calendar, Date and JodaTime classes passed to be validated is in the past.
 */
class PastValidatorForOption extends ConstraintValidator[Past, Option[_]] {
  private var constraintAnnotation: Past = _

  override def initialize(constraintAnnotation: Past): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new TimePastFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
