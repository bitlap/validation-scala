package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Pattern

import org.hibernate.validator.internal.constraintvalidators.bv.PatternValidator

import bitlap.validation.Utils._

/**
 * Check the wrapped string.
 */
class PatternValidatorForOption extends ConstraintValidator[Pattern, IterableOnce[_]] {
  private var constraintAnnotation: Pattern = _

  override def initialize(constraintAnnotation: Pattern): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: CharSequence) =>
        val v = new PatternValidator
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
