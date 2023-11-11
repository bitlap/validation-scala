package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.DecimalMinFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.DecimalMin

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the wrapped number being validated is less than
 * or equal to the maximum value specified.
 */
class DecimalMinValidatorForOption extends ConstraintValidator[DecimalMin, IterableOnce[_]] {
  private var function: DecimalMinFunction = _

  override def initialize(constraintAnnotation: DecimalMin): Unit =
    function = new DecimalMinFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
