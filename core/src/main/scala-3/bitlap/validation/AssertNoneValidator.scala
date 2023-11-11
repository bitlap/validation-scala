package bitlap.validation

import bitlap.validation.Utils._
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Validates that the wrapped value passed is none
 */
class AssertNoneValidator extends ConstraintValidator[AssertNone, IterableOnce[_]] {
  // because the generic parameter cannot be obtained, the validator sees Option instead of Option<Object>

  override def initialize(constraintAnnotation: AssertNone): Unit = {}

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) { x =>
      x.isEmpty
    }
}
