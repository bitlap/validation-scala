package bitlap.validation

import org.hibernate.validator.constraints.Mod11Check

import bitlap.validation.Utils._
import bitlap.validation.function.Mod11CheckFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Mod11 Check Digit validator for scala.
 *
 * http://en.wikipedia.org/wiki/Check_digit
 */
class Mod11CheckValidatorForOption extends ConstraintValidator[Mod11Check, IterableOnce[_]] {
  private var function: Mod11CheckFunction = _

  override def initialize(constraintAnnotation: Mod11Check): Unit =
    function = Mod11CheckFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
