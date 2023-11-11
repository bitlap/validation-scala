package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Min

final case class MinFunction(annotation: Min) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new MinValidatorForCharSequence
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Number)       =>
        val v = new MinValidatorForNumber
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case Some(_)               =>
        throw new IllegalStateException("oops.")
    }
}
