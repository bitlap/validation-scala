package bitlap.validation.function

import javax.validation.ConstraintValidatorContext

import org.hibernate.validator.constraints.NotBlank
import org.hibernate.validator.internal.constraintvalidators.hv.NotBlankValidator

final case class NotBlankFunction(val annotation: NotBlank) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new NotBlankValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
