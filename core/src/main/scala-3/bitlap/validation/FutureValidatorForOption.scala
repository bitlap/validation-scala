package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Future

import bitlap.validation.Utils._
import bitlap.validation.function.TimeFutureFunction

/**
 * Check that the wrapped Calendar, Date and JodaTime classes passed to be validated is in the future.
 */
class FutureValidatorForOption extends ConstraintValidator[Future, IterableOnce[_]] {
  private var constraintAnnotation: Future = _

  override def initialize(constraintAnnotation: Future): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new TimeFutureFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
