package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.Mod11Check

import bitlap.validation.Utils._
import bitlap.validation.function.Mod11CheckFunction

/**
 * Mod11 Check Digit validator for scala.
 *
 * http://en.wikipedia.org/wiki/Check_digit
 */
class Mod11CheckValidatorForOption extends ConstraintValidator[Mod11Check, IterableOnce[_]] {
  private var constraintAnnotation: Mod11Check = _

  override def initialize(constraintAnnotation: Mod11Check): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new Mod11CheckFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
