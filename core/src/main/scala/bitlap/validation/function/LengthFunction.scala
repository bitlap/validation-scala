package bitlap.validation.function

import javax.validation.ConstraintValidatorContext

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.internal.constraintvalidators.hv.{ LengthValidator => JavaLengthValidator }

final case class LengthFunction(val annotation: Length) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new JavaLengthValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
