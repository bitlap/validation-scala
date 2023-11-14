package bitlap.validation

import org.hibernate.validator.constraints.LuhnCheck

import bitlap.validation.function.LuhnCheckFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Luhn algorithm checksum validator for scala.
 *
 * http://en.wikipedia.org/wiki/Luhn_algorithm http://en.wikipedia.org/wiki/Check_digit
 */
class LuhnCheckValidatorForOption extends ConstraintValidator[LuhnCheck, Option[_]] {
  private var function: LuhnCheckFunction = _

  override def initialize(constraintAnnotation: LuhnCheck): Unit =
    function = new LuhnCheckFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
