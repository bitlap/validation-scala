package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import bitlap.validation.Utils._

/**
 * Validates that the wrapped value passed is none
 */
class AssertNoneValidator extends ConstraintValidator[AssertNone, IterableOnce[_]] {

  override def initialize(constraintAnnotation: AssertNone): Unit = {}

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) { x =>
      x.isEmpty
    }
}
