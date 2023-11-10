package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import bitlap.validation.function.ByteSizeFunction

/**
 * Check that the length of a wrapped value is between min and max.
 */
class ByteSizeValidator extends ConstraintValidator[ByteSize, Any] {
  private var min: Int            = 0
  private var max: Int            = 0
  private var charsetName: String = _

  override def initialize(constraintAnnotation: ByteSize): Unit = {
    min = constraintAnnotation.min()
    max = constraintAnnotation.max()
    charsetName = constraintAnnotation.charsetName()
    function.validateParameters()
  }

  private lazy val function = new ByteSizeFunction(min, min, charsetName)

  override def isValid(value: Any, context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)

}
