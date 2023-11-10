package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Future

import bitlap.validation.function.TimeFutureFunction

/**
 * Check that the wrapped Calendar, Date and JodaTime classes passed to be validated is in the future.
 */
class FutureValidatorForOption extends ConstraintValidator[Future, Option[_]] {
  private var constraintAnnotation: Future = _

  override def initialize(constraintAnnotation: Future): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new TimeFutureFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
