package bitlap.validation

import org.hibernate.validator.constraints.EAN

import bitlap.validation.Utils._
import bitlap.validation.function.EANFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Checks that a given wrapped character sequence (e.g. Option[String]) is a valid EAN barcode.
 */
class EANValidatorForOption extends ConstraintValidator[EAN, IterableOnce[_]] {
  private var function: EANFunction = _

  override def initialize(constraintAnnotation: EAN): Unit =
    function = EANFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
