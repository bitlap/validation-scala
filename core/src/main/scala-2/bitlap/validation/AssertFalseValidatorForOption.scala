package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.AssertFalse

import bitlap.validation.function._

/**
 * Validates that the wrapped value passed is false
 */
class AssertFalseValidatorForOption extends ConstraintValidator[AssertFalse, Option[Boolean]] {
  private var constraintAnnotation: AssertFalse = _

  override def initialize(constraintAnnotation: AssertFalse): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = AssertFalseFunction(constraintAnnotation)

  override def isValid(value: Option[Boolean], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
