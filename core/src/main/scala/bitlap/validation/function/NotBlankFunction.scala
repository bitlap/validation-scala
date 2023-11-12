package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.NotBlank

final case class NotBlankFunction(annotation: NotBlank) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new NotBlankValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case _                     => throw new IllegalStateException("oops.")
    }
}
