package bitlap.validation

import bitlap.validation.function._
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.AssertFalse

/**
 * Validates that the wrapped value passed is false
 */
class AssertFalseValidatorForOption extends ConstraintValidator[AssertFalse, Option[Boolean]] {
  private var function: AssertFalseFunction = _

  override def initialize(constraintAnnotation: AssertFalse): Unit =
    function = AssertFalseFunction(constraintAnnotation)

  override def isValid(value: Option[Boolean], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
