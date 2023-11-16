package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.AssertTrueFunction
import jakarta.validation.constraints.AssertTrue
import jakarta.validation.{ConstraintValidator, ConstraintValidatorContext}

/**
 * Validates that the wrapped value passed is true
 */
class AssertTrueValidatorForOption extends ConstraintValidator[AssertTrue, IterableOnce[_]] {
  // the validator obtained `Option` instead of `Option<Object>`, because the generic parameter cannot be obtained.
  private var function: AssertTrueFunction = _

  override def initialize(constraintAnnotation: AssertTrue): Unit =
    function = AssertTrueFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
