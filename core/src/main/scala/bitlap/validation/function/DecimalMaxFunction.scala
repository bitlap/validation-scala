package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv._
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.DecimalMax

final case class DecimalMaxFunction(annotation: DecimalMax) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: CharSequence) =>
        val v = new DecimalMaxValidatorForCharSequence
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Number)       =>
        val v = new DecimalMaxValidatorForNumber
        v.initialize(annotation)
        v.isValid(x, ctx)
      case None                  =>
        true
      case _                     => throw new IllegalStateException("oops.")
    }
}
