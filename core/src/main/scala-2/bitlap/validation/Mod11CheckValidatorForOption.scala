package bitlap.validation

import org.hibernate.validator.constraints.Mod11Check

import bitlap.validation.function.Mod11CheckFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Mod11 Check Digit validator for scala.
 *
 * http://en.wikipedia.org/wiki/Check_digit
 */
class Mod11CheckValidatorForOption extends ConstraintValidator[Mod11Check, Option[_]] {
  private var function: Mod11CheckFunction = _

  override def initialize(constraintAnnotation: Mod11Check): Unit =
    function = new Mod11CheckFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
