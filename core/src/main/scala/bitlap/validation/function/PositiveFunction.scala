package bitlap.validation.function

import java.util

import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.constraintvalidators.bv.number.sign._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.{ Positive, Size }

final case class PositiveFunction(annotation: Positive) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x) =>
        x match {
          case x: CharSequence         =>
            val v = new PositiveValidatorForCharSequence
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Byte                 =>
            val v = new PositiveValidatorForByte
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Float                =>
            val v = new PositiveValidatorForFloat
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Double =>
            val v = new PositiveValidatorForDouble
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Short                =>
            val v = new PositiveValidatorForShort
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Int                  =>
            val v = new PositiveValidatorForInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: Long =>
            val v = new PositiveValidatorForLong
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: BigDecimal           =>
            val v = new PositiveValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x.underlying(), ctx)
          case x: java.math.BigDecimal =>
            val v = new PositiveValidatorForBigDecimal
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.math.BigInteger           =>
            val v = new PositiveValidatorForBigInteger
            v.initialize(annotation)
            v.isValid(x, ctx)
          case _ => throw new IllegalStateException("oops.")
        }
      case None    =>
        true
    }
}
