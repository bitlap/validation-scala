package bitlap.validation

import bitlap.validation.function.NotEmptyFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.NotEmpty

/**
 * Check that the wrapped CharSequence, Collection, Map, Array, Seq and Set classes passed to be validated is not empty.
 */
class NotEmptyValidatorForOption extends ConstraintValidator[NotEmpty, Option[_]] {
  private var function: NotEmptyFunction = _

  override def initialize(constraintAnnotation: NotEmpty): Unit =
    function = new NotEmptyFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
