package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext, ValidationException }
import javax.validation.constraints.AssertTrue

import bitlap.validation.Utils._
import bitlap.validation.function.AssertTrueFunction

/**
 * Validates that the wrapped value passed is true
 */
class AssertTrueValidatorForOption extends ConstraintValidator[AssertTrue, IterableOnce[_]] {
  private var constraintAnnotation: AssertTrue = _

  override def initialize(constraintAnnotation: AssertTrue): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = AssertTrueFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
