package bitlap.validation.function

import java.time.{ Instant => JavaInstant }
import java.util.{ Calendar, Date }

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorInitializationContext
import org.hibernate.validator.internal.constraintvalidators.bv.time.future._
import org.joda.time.{ ReadableInstant, ReadablePartial }

import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Future
import jakarta.validation.metadata.ConstraintDescriptor

final case class TimeFutureFunction(
  annotation: ConstraintDescriptor[Future],
  context: HibernateConstraintValidatorInitializationContext
) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Calendar)        =>
        val v = new FutureValidatorForCalendar
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: Date)            =>
        val v = new FutureValidatorForDate
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: JavaInstant)     =>
        val v = new FutureValidatorForInstant
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: ReadableInstant) =>
        val v = new FutureValidatorForReadableInstant
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case Some(x: ReadablePartial) =>
        val v = new FutureValidatorForReadablePartial
        v.initialize(annotation, context)
        v.isValid(x, ctx)
      case None                     =>
        true
      case _                        =>
        throw new IllegalStateException("oops.")
    }
}
