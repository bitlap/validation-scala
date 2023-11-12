package bitlap.validation

import bitlap.validation.Utils._
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Validates that the wrapped value passed is none
 */
class AssertNoneValidator extends ConstraintValidator[AssertNone, IterableOnce[_]] {
  // the validator obtained `Option` instead of `Option<Object>`, because the generic parameter cannot be obtained.

  override def initialize(constraintAnnotation: AssertNone): Unit = {}

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) { x =>
      x.isEmpty
    }
}
