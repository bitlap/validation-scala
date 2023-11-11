package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.{ EmailValidator => JavaEmailValidator }

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Email

final case class EmailFunction(annotation: Email) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new JavaEmailValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
