package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext, ValidationException }
import javax.validation.constraints.AssertTrue

import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator

import bitlap.validation.Utils._

/**
 * Validates that the wrapped value passed is true
 */
class AssertTrueValidatorForOption extends ConstraintValidator[AssertTrue, IterableOnce[_]] {
  private var constraintAnnotation: AssertTrue = _

  override def initialize(constraintAnnotation: AssertTrue): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: Boolean) =>
        val v = new AssertTrueValidator
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(_)          =>
        throw new ValidationException("Unsupported type.")
      case None             =>
        true
    }
}
