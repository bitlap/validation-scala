package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.LuhnCheck

import bitlap.validation.function.LuhnCheckFunction

/**
 * Luhn algorithm checksum validator for scala.
 *
 * http://en.wikipedia.org/wiki/Luhn_algorithm http://en.wikipedia.org/wiki/Check_digit
 */
class LuhnCheckValidatorForOption extends ConstraintValidator[LuhnCheck, Option[_]] {
  private var constraintAnnotation: LuhnCheck = _

  override def initialize(constraintAnnotation: LuhnCheck): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new LuhnCheckFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
