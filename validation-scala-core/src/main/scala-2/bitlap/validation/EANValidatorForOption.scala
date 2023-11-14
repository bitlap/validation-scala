package bitlap.validation

import org.hibernate.validator.constraints.EAN

import bitlap.validation.function.EANFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Checks that a given wrapped character sequence (e.g. Option[String]) is a valid EAN barcode.
 */
class EANValidatorForOption extends ConstraintValidator[EAN, Option[_]] {
  private var function: EANFunction = _

  override def initialize(constraintAnnotation: EAN): Unit =
    function = new EANFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
