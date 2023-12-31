package bitlap.validation.function

import org.hibernate.validator.constraints.Mod10Check
import org.hibernate.validator.internal.constraintvalidators.hv.Mod10CheckValidator

import jakarta.validation.ConstraintValidatorContext

final case class Mod10CheckFunction(annotation: Mod10Check) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new Mod10CheckValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case _                     =>
        throw new IllegalStateException("oops.")
    }
}
