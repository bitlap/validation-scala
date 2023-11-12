package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.AssertTrue

final case class AssertTrueFunction(val annotation: AssertTrue) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Boolean)           =>
        val v = new AssertTrueValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: java.lang.Boolean) =>
        val v = new AssertTrueValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                       =>
        true
      case _                          =>
        throw new IllegalStateException("oops.")
    }
}
