package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.PatternFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Pattern

/**
 * Check the wrapped string.
 */
class PatternValidatorForOption extends ConstraintValidator[Pattern, IterableOnce[_]] {
  private var function: PatternFunction = _

  override def initialize(constraintAnnotation: Pattern): Unit =
    function = new PatternFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
