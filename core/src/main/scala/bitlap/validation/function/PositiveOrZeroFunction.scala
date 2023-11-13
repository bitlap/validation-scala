package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.number.sign._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.PositiveOrZero

final case class PositiveOrZeroFunction(annotation: PositiveOrZero) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x) =>
        x match {
          case x: CharSequence         =>
            val v = new PositiveOrZeroValidatorForCharSequence
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Byte                 =>
            val v = new PositiveOrZeroValidatorForByte
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Float                =>
            val v = new PositiveOrZeroValidatorForFloat
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Double               =>
            val v = new PositiveOrZeroValidatorForDouble
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Short                =>
            val v = new PositiveOrZeroValidatorForShort
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Int                  =>
            val v = new PositiveOrZeroValidatorForInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Long                 =>
            val v = new PositiveOrZeroValidatorForLong
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: BigDecimal           =>
            val v = new PositiveOrZeroValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x.underlying(), ctx)
          case x: java.math.BigDecimal =>
            val v = new PositiveOrZeroValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.math.BigInteger =>
            val v = new PositiveOrZeroValidatorForBigInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case _                       => throw new IllegalStateException("oops.")
        }
      case None    =>
        true
    }
}
