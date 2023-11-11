package bitlap.validation.function

import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.Pattern

import org.hibernate.validator.internal.constraintvalidators.bv.PatternValidator

final case class PatternFunction(annotation: Pattern) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new PatternValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
