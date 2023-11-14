package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.PatternValidator

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Pattern

final case class PatternFunction(annotation: Pattern) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new PatternValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case _                     =>
        throw new IllegalStateException("oops.")
    }
}
