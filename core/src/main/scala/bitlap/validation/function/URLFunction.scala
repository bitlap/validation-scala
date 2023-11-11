package bitlap.validation.function

import org.hibernate.validator.constraints.URL
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator

import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }

final case class URLFunction(annotation: URL) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new URLValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
