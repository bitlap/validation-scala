package bitlap.validation.function

import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.AssertFalse

import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator

final case class AssertFalseFunction(val annotation: AssertFalse) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Boolean) =>
        val v = new AssertFalseValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case _                =>
        true
    }
}
