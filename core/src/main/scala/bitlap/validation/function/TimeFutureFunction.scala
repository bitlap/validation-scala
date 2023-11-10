package bitlap.validation.function

import java.time.{ Instant => JavaInstant }
import java.util.{ Calendar, Date }
import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.Future

import org.hibernate.validator.internal.constraintvalidators.bv.future._
import org.joda.time.{ ReadableInstant, ReadablePartial }

final class TimeFutureFunction(val annotation: Future) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Calendar)        =>
        val v = new FutureValidatorForCalendar
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Date)            =>
        val v = new FutureValidatorForDate
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: JavaInstant)     =>
        val v = new FutureValidatorForInstant
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: ReadableInstant) =>
        val v = new FutureValidatorForReadableInstant
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: ReadablePartial) =>
        val v = new FutureValidatorForReadablePartial
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                     =>
        true
      case Some(_)                  =>
        throw new IllegalStateException("oops.")
    }
}
