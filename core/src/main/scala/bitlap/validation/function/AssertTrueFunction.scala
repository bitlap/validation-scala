package bitlap.validation.function

import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.AssertTrue

import org.hibernate.validator.internal.constraintvalidators.bv.AssertTrueValidator

final case class AssertTrueFunction(val annotation: AssertTrue) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Boolean) =>
        val v = new AssertTrueValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case _                =>
        true
    }
}
