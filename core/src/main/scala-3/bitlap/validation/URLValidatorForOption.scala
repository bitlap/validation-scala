package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }

import org.hibernate.validator.constraints.URL

import bitlap.validation.Utils._
import bitlap.validation.function.URLFunction

/**
 * Validate that the wrapped character sequence (e.g. Option[String]) is a valid URL.
 */
class URLValidatorForOption extends ConstraintValidator[URL, IterableOnce[_]] {
  private var constraintAnnotation: URL = _

  override def initialize(constraintAnnotation: URL): Unit =
    this.constraintAnnotation = constraintAnnotation

  private lazy val function = new URLFunction(constraintAnnotation)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
