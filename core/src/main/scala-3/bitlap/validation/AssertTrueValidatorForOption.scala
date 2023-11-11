package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.AssertTrueFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext, ValidationException }
import jakarta.validation.constraints.AssertTrue

/**
 * Validates that the wrapped value passed is true
 */
class AssertTrueValidatorForOption extends ConstraintValidator[AssertTrue, IterableOnce[_]] {
  private var function: AssertTrueFunction = _

  override def initialize(constraintAnnotation: AssertTrue): Unit =
    function = AssertTrueFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
