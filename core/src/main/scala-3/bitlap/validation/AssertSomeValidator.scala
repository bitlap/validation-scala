package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.constraints.AssertSome
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Validates that the wrapped value passed is some
 */
class AssertSomeValidator extends ConstraintValidator[AssertSome, IterableOnce[_]] {

  override def initialize(constraintAnnotation: AssertSome): Unit = {}

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) { x =>
      x.isDefined
    }
}
