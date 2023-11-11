package bitlap.validation.function

import org.hibernate.validator.constraints.EAN
import org.hibernate.validator.internal.constraintvalidators.hv.{ EANValidator => JavaEANValidator }

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Digits

final case class EANFunction(annotation: EAN) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new JavaEANValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
