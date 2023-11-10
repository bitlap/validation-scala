package bitlap.validation

import java.util
import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Size

import bitlap.validation.Utils._
import bitlap.validation.function.SizeFunction

/**
 * Check that the length of a wrapped value is between min and max.
 */
class SizeValidatorForOption extends ConstraintValidator[Size, IterableOnce[_]] {

  private var constraintAnnotation: Size = _

  override def initialize(constraintAnnotation: Size): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new SizeFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))

}
