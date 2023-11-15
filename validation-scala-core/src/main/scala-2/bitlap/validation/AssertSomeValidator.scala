package bitlap.validation

import bitlap.validation.constraints.AssertSome
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

/**
 * Validates that the wrapped value passed is some
 */
class AssertSomeValidator extends ConstraintValidator[AssertSome, Option[_]] {

  override def initialize(constraintAnnotation: AssertSome): Unit = {}

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    value.isDefined
}
