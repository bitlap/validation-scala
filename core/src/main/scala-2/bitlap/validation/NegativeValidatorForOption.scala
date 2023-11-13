package bitlap.validation

import bitlap.validation.function.NegativeFunction
import jakarta.validation.constraints.Negative
import jakarta.validation.{ConstraintValidator, ConstraintValidatorContext}

/**
 * Validates that the wrapped value passed is a strictly negative number.
 */
class NegativeValidatorForOption extends ConstraintValidator[Negative, Option[_]] {

  private var function: NegativeFunction = _

  override def initialize(constraintAnnotation: Negative): Unit =
    function = new NegativeFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)

}
