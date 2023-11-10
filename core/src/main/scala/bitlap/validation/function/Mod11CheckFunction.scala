package bitlap.validation.function

import javax.validation.ConstraintValidatorContext

import org.hibernate.validator.constraints.Mod11Check
import org.hibernate.validator.internal.constraintvalidators.hv.Mod11CheckValidator

final class Mod11CheckFunction(val annotation: Mod11Check) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new Mod11CheckValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
