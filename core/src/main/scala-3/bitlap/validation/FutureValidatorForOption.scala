package bitlap.validation

import java.util.{ Calendar, Date }
import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Future

import org.hibernate.validator.internal.constraintvalidators.bv.future.{
  FutureValidatorForCalendar,
  FutureValidatorForDate,
  FutureValidatorForReadableInstant,
  FutureValidatorForReadablePartial
}
import org.joda.time.{ ReadableInstant, ReadablePartial }

import bitlap.validation.Utils._

/**
 * Check that the wrapped Calendar, Date and JodaTime classes passed to be validated is in the future.
 */
class FutureValidatorForOption extends ConstraintValidator[Future, IterableOnce[_]] {
  private var constraintAnnotation: Future = _

  override def initialize(constraintAnnotation: Future): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: Calendar)        =>
        val v = new FutureValidatorForCalendar
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Date)            =>
        val v = new FutureValidatorForDate
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: ReadableInstant) =>
        val v = new FutureValidatorForReadableInstant
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: ReadablePartial) =>
        val v = new FutureValidatorForReadablePartial
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case None                     =>
        true
      case Some(_)                  =>
        throw new IllegalStateException("oops.")
    }

}
