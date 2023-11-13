package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.AssertFalse

final case class AssertFalseFunction(annotation: AssertFalse) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Boolean)           =>
        val v = new AssertFalseValidator
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                       =>
        true
      case _                          =>
        throw new IllegalStateException("oops.")
    }
}
