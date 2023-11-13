package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.number.bound._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.Max

final case class MaxFunction(annotation: Max) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x) =>
        // collection or seq
        x match {
          case x: CharSequence =>
            val v = new MaxValidatorForCharSequence
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Byte                 =>
            val v = new MaxValidatorForByte
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Float                =>
            val v = new MaxValidatorForFloat
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Double               =>
            val v = new MaxValidatorForDouble
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Short                =>
            val v = new MaxValidatorForShort
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Int                  =>
            val v = new MaxValidatorForInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Long                 =>
            val v = new MaxValidatorForLong
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: BigDecimal           =>
            val v = new MaxValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x.underlying(), ctx)
          case x: java.math.BigDecimal =>
            val v = new MaxValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.math.BigInteger =>
            val v = new MaxValidatorForBigInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case _                       => throw new IllegalStateException("oops.")
        }
      case None    =>
        true
    }
}
