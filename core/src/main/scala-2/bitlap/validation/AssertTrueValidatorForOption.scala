package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.AssertTrue

import bitlap.validation.function.AssertTrueFunction

/**
 * Validates that the wrapped value passed is true
 */
class AssertTrueValidatorForOption extends ConstraintValidator[AssertTrue, Option[Boolean]] {
  private var constraintAnnotation: AssertTrue = _

  override def initialize(constraintAnnotation: AssertTrue): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = AssertTrueFunction(constraintAnnotation)

  override def isValid(value: Option[Boolean], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
