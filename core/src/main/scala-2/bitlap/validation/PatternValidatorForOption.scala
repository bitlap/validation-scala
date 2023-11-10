package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Pattern

import bitlap.validation.function.PatternFunction

/**
 * Check the wrapped string.
 */
class PatternValidatorForOption extends ConstraintValidator[Pattern, Option[_]] {
  private var constraintAnnotation: Pattern = _

  override def initialize(constraintAnnotation: Pattern): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new PatternFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
