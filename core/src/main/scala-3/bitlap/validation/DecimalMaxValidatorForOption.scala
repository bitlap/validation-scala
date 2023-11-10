package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.DecimalMax

import org.hibernate.validator.internal.constraintvalidators.bv.{
  DecimalMaxValidatorForCharSequence,
  DecimalMaxValidatorForNumber
}

import bitlap.validation.Utils._

/**
 * Check that the wrapped character sequence (e.g. Option[String]) and the wrapped number being validated represents a
 * number, and has a value less than or equal to the maximum value specified.
 */
class DecimalMaxValidatorForOption extends ConstraintValidator[DecimalMax, IterableOnce[_]] {
  private var constraintAnnotation: DecimalMax = _

  override def initialize(constraintAnnotation: DecimalMax): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: CharSequence) =>
        val v = new DecimalMaxValidatorForCharSequence
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Number)       =>
        val v = new DecimalMaxValidatorForNumber
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
