package bitlap.validation

import java.util
import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Size

import bitlap.validation.function.SizeFunction

/**
 * Check that the length of a wrapped value is between min and max.
 */
class SizeValidatorForOption extends ConstraintValidator[Size, Option[_]] {
  private var constraintAnnotation: Size = _

  override def initialize(constraintAnnotation: Size): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new SizeFunction(constraintAnnotation)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
