package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.AssertFalseFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.AssertFalse

/**
 * Validates that the wrapped value passed is false
 */
class AssertFalseValidatorForOption extends ConstraintValidator[AssertFalse, IterableOnce[_]] {
  private var constraintAnnotation: AssertFalse = _

  override def initialize(constraintAnnotation: AssertFalse): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = AssertFalseFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
