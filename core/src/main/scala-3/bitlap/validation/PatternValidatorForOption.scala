package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Pattern

import bitlap.validation.Utils._
import bitlap.validation.function.PatternFunction

/**
 * Check the wrapped string.
 */
class PatternValidatorForOption extends ConstraintValidator[Pattern, IterableOnce[_]] {
  private var constraintAnnotation: Pattern = _

  override def initialize(constraintAnnotation: Pattern): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new PatternFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
