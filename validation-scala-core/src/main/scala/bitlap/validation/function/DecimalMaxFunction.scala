package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv._
import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.DecimalMax

final case class DecimalMaxFunction(annotation: DecimalMax) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x) =>
        x match {
          case x: CharSequence         =>
            val v = new DecimalMaxValidatorForCharSequence
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Byte                 =>
            val v = new DecimalMaxValidatorForByte
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Float                =>
            val v = new DecimalMaxValidatorForFloat
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Double               =>
            val v = new DecimalMaxValidatorForDouble
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Short                =>
            val v = new DecimalMaxValidatorForShort
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Int                  =>
            val v = new DecimalMaxValidatorForInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Long                 =>
            val v = new DecimalMaxValidatorForLong
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: BigDecimal           =>
            val v = new DecimalMaxValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x.underlying(), ctx)
          case x: java.math.BigDecimal =>
            val v = new DecimalMaxValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.math.BigInteger =>
            val v = new DecimalMaxValidatorForBigInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case _                       => throw new IllegalStateException("oops.")
        }
      case None    =>
        true
    }
}
