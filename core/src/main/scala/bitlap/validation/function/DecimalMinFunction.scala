package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound.decimal._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.DecimalMin

final case class DecimalMinFunction(annotation: DecimalMin) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x) =>
        x match {
          case x: CharSequence         =>
            val v = new DecimalMinValidatorForCharSequence
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Byte                 =>
            val v = new DecimalMinValidatorForByte
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Float                =>
            val v = new DecimalMinValidatorForFloat
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Double               =>
            val v = new DecimalMinValidatorForDouble
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Short                =>
            val v = new DecimalMinValidatorForShort
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Int                  =>
            val v = new DecimalMinValidatorForInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Long                 =>
            val v = new DecimalMinValidatorForLong
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: BigDecimal           =>
            val v = new DecimalMinValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x.underlying(), ctx)
          case x: java.math.BigDecimal =>
            val v = new DecimalMinValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.math.BigInteger =>
            val v = new DecimalMinValidatorForBigInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case _                       => throw new IllegalStateException("oops.")
        }
      case None    =>
        true
    }
}
