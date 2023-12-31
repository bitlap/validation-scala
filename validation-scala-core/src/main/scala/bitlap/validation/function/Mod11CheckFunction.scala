package bitlap.validation.function

import org.hibernate.validator.constraints.Mod11Check
import org.hibernate.validator.internal.constraintvalidators.hv.Mod11CheckValidator

import jakarta.validation.ConstraintValidatorContext

final case class Mod11CheckFunction(annotation: Mod11Check) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new Mod11CheckValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case _                     =>
        throw new IllegalStateException("oops.")
    }
}
