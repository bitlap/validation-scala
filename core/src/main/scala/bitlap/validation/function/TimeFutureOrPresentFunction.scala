package bitlap.validation.function

import java.time.{ Instant => JavaInstant }
import java.util.{ Calendar, Date }

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent._
import org.joda.time.{ ReadableInstant, ReadablePartial }

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.metadata.ConstraintDescriptor

final case class TimeFutureOrPresentFunction(
  annotation: ConstraintDescriptor[FutureOrPresent],
  context: HibernateConstraintValidatorInitializationContext
) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Calendar)        =>
        val v = new FutureOrPresentValidatorForCalendar
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: Date)            =>
        val v = new FutureOrPresentValidatorForDate
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: JavaInstant)     =>
        val v = new FutureOrPresentValidatorForInstant
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: ReadableInstant) =>
        val v = new FutureOrPresentValidatorForReadableInstant
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: ReadablePartial) =>
        val v = new FutureOrPresentValidatorForReadablePartial
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case None                     =>
        true
      case Some(_)                  =>
        throw new IllegalStateException("oops.")
    }
}
