package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.number.sign._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Negative

final case class NegativeFunction(annotation: Negative) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x) =>
        x match {
          case x: CharSequence         =>
            val v = new NegativeValidatorForCharSequence
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Byte                 =>
            val v = new NegativeValidatorForByte
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Float                =>
            val v = new NegativeValidatorForFloat
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Double               =>
            val v = new NegativeValidatorForDouble
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Short                =>
            val v = new NegativeValidatorForShort
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Int                  =>
            val v = new NegativeValidatorForInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Long                 =>
            val v = new NegativeValidatorForLong
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: BigDecimal           =>
            val v = new NegativeValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x.underlying(), ctx)
          case x: java.math.BigDecimal =>
            val v = new NegativeValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.math.BigInteger =>
            val v = new NegativeValidatorForBigInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case _                       => throw new IllegalStateException("oops.")
        }
      case None    =>
        true
    }
}
