package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.EAN

import bitlap.validation.Utils._
import bitlap.validation.function.EANFunction

/**
 * Checks that a given wrapped character sequence (e.g. Option[String]) is a valid EAN barcode.
 */
class EANValidatorForOption extends ConstraintValidator[EAN, IterableOnce[_]] {
  private var constraintAnnotation: EAN = _

  override def initialize(constraintAnnotation: EAN): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new EANFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
