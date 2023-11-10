package bitlap.validation

import java.util.{ Calendar, Date }
import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Past

import org.hibernate.validator.internal.constraintvalidators.bv.past.{
  PastValidatorForCalendar,
  PastValidatorForDate,
  PastValidatorForReadableInstant,
  PastValidatorForReadablePartial
}
import org.joda.time.{ ReadableInstant, ReadablePartial }

import bitlap.validation.Utils._

/**
 * Check that the wrapped Calendar, Date and JodaTime classes passed to be validated is in the past.
 */
class PastValidatorForOption extends ConstraintValidator[Past, IterableOnce[_]] {
  private var constraintAnnotation: Past = _

  override def initialize(constraintAnnotation: Past): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: Calendar)        =>
        val v = new PastValidatorForCalendar
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Date)            =>
        val v = new PastValidatorForDate
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: ReadableInstant) =>
        val v = new PastValidatorForReadableInstant
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: ReadablePartial) =>
        val v = new PastValidatorForReadablePartial
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case None                     =>
        true
      case Some(_)                  =>
        throw new IllegalStateException("oops.")
    }
}
