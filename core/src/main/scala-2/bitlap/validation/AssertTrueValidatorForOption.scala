package bitlap.validation

import bitlap.validation.function.AssertTrueFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.AssertTrue

/**
 * Validates that the wrapped value passed is true
 */
class AssertTrueValidatorForOption extends ConstraintValidator[AssertTrue, Option[Boolean]] {
  private var function: AssertTrueFunction = _

  override def initialize(constraintAnnotation: AssertTrue): Unit =
    function = AssertTrueFunction(constraintAnnotation)

  override def isValid(value: Option[Boolean], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
