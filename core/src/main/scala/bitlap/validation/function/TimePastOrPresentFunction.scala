package bitlap.validation.function

import java.time.{ Instant => JavaInstant }
import java.util.{ Calendar, Date }

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent._
import org.joda.time.{ ReadableInstant, ReadablePartial }

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.metadata.ConstraintDescriptor

final case class TimePastOrPresentFunction(
  annotation: ConstraintDescriptor[PastOrPresent],
  context: HibernateConstraintValidatorInitializationContext
) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Calendar)        =>
        val v = new PastOrPresentValidatorForCalendar
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: Date)            =>
        val v = new PastOrPresentValidatorForDate
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: JavaInstant)     =>
        val v = new PastOrPresentValidatorForInstant
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: ReadableInstant) =>
        val v = new PastOrPresentValidatorForReadableInstant
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: ReadablePartial) =>
        val v = new PastOrPresentValidatorForReadablePartial
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case None                     =>
        true
      case Some(_)                  =>
        throw new IllegalStateException("oops.")
    }
}
