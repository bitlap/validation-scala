package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Past

import bitlap.validation.Utils._
import bitlap.validation.function.TimePastFunction

/**
 * Check that the wrapped Calendar, Date and JodaTime classes passed to be validated is in the past.
 */
class PastValidatorForOption extends ConstraintValidator[Past, IterableOnce[_]] {
  private var constraintAnnotation: Past = _

  override def initialize(constraintAnnotation: Past): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new TimePastFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
