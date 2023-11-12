package bitlap.validation

import bitlap.validation.Utils.checkForOption
import bitlap.validation.function.NotEmptyFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.NotEmpty

/**
 * Check that the wrapped CharSequence, Collection, Map, Array, Seq and Set classes passed to be validated is not empty or not null.
 */
class NotEmptyValidatorForOption extends ConstraintValidator[NotEmpty, IterableOnce[_]] {
  private var function: NotEmptyFunction = _

  override def initialize(constraintAnnotation: NotEmpty): Unit =
    function = new NotEmptyFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
