package bitlap.validation.function

import javax.validation.ConstraintValidatorContext

import org.hibernate.validator.constraints.Email
import org.hibernate.validator.internal.constraintvalidators.hv.{ EmailValidator => JavaEmailValidator }

final class EmailFunction(val annotation: Email) extends CheckOptionFunction {

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
