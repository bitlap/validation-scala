package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext, ValidationException }
import javax.validation.constraints.AssertFalse

import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator

import bitlap.validation.Utils._

/**
 * Validates that the wrapped value passed is false
 */
class AssertFalseValidatorForOption extends ConstraintValidator[AssertFalse, IterableOnce[_]] {
  private var constraintAnnotation: AssertFalse = _

  override def initialize(constraintAnnotation: AssertFalse): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: Boolean) =>
        val v = new AssertFalseValidator
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(_)          =>
        throw new ValidationException("Unsupported type.")
      case None             =>
        true
    }
}
