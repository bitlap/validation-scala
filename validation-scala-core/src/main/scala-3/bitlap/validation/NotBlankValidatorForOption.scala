package bitlap.validation

import bitlap.validation.Utils._
import bitlap.validation.function.NotBlankFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.NotBlank

/**
 * Check that a wrapped character sequence's (e.g. Option[String]) trimmed length is not empty.
 */
class NotBlankValidatorForOption extends ConstraintValidator[NotBlank, IterableOnce[_]] {
  private var function: NotBlankFunction = _

  override def initialize(constraintAnnotation: NotBlank): Unit =
    function = NotBlankFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
