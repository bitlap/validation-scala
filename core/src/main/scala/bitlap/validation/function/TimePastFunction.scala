package bitlap.validation.function

import java.time.{ Instant => JavaInstant }
import java.util.{ Calendar, Date }
import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.Past

import org.hibernate.validator.internal.constraintvalidators.bv.past._
import org.joda.time.{ ReadableInstant, ReadablePartial }

final case class TimePastFunction(annotation: Past) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Calendar)        =>
        val v = new PastValidatorForCalendar
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Date)            =>
        val v = new PastValidatorForDate
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: JavaInstant)     =>
        val v = new PastValidatorForInstant
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: ReadableInstant) =>
        val v = new PastValidatorForReadableInstant
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: ReadablePartial) =>
        val v = new PastValidatorForReadablePartial
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                     =>
        true
      case Some(_)                  =>
        throw new IllegalStateException("oops.")
    }
}
