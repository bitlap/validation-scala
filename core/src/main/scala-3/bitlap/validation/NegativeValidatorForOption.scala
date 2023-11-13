package bitlap.validation

import bitlap.validation.Utils.checkForOption
import bitlap.validation.function.NegativeFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Negative

/**
 * Validates that the wrapped value passed is a strictly negative number.
 */
class NegativeValidatorForOption extends ConstraintValidator[Negative, IterableOnce[_]] {

  private var function: NegativeFunction = _

  override def initialize(constraintAnnotation: Negative): Unit =
    function = new NegativeFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))

}
