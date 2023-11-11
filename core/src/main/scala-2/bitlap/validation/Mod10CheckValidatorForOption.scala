package bitlap.validation

import org.hibernate.validator.constraints.Mod10Check

import bitlap.validation.function.Mod10CheckFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Mod10 (Luhn algorithm implementation) Check validator for scala.
 *
 * http://en.wikipedia.org/wiki/Luhn_algorithm http://en.wikipedia.org/wiki/Check_digit
 */
class Mod10CheckValidatorForOption extends ConstraintValidator[Mod10Check, Option[_]] {
  private var function: Mod10CheckFunction = _

  override def initialize(constraintAnnotation: Mod10Check): Unit =
    function = new Mod10CheckFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
