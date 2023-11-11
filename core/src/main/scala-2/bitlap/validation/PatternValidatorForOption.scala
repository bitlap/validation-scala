package bitlap.validation

import bitlap.validation.function.PatternFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Pattern

/**
 * Check the wrapped string.
 */
class PatternValidatorForOption extends ConstraintValidator[Pattern, Option[_]] {
  private var function: PatternFunction = _

  override def initialize(constraintAnnotation: Pattern): Unit =
    function = new PatternFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
