package bitlap.validation.function

import org.hibernate.validator.constraints.LuhnCheck
import org.hibernate.validator.internal.constraintvalidators.hv.{ LuhnCheckValidator => JavaLuhnCheckValidator }

import jakarta.validation.ConstraintValidatorContext

final case class LuhnCheckFunction(annotation: LuhnCheck) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new JavaLuhnCheckValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
