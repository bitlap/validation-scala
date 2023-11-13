package bitlap.validation.function

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.NegativeOrZero
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign._

final case class NegativeOrZeroFunction(annotation: NegativeOrZero) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x) =>
        x match {
          case x: CharSequence =>
            val v = new NegativeOrZeroValidatorForCharSequence
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Byte =>
            val v = new NegativeOrZeroValidatorForByte
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Float =>
            val v = new NegativeOrZeroValidatorForFloat
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Double =>
            val v = new NegativeOrZeroValidatorForDouble
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Short =>
            val v = new NegativeOrZeroValidatorForShort
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Int =>
            val v = new NegativeOrZeroValidatorForInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Long =>
            val v = new NegativeOrZeroValidatorForLong
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: BigDecimal =>
            val v = new NegativeOrZeroValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x.underlying(), ctx)
          case x: java.math.BigDecimal =>
            val v = new NegativeOrZeroValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.math.BigInteger =>
            val v = new NegativeOrZeroValidatorForBigInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case _ => throw new IllegalStateException("oops.")
        }
      case None =>
        true
    }
}
