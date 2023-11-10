package bitlap.validation.function

import java.util
import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Size

import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.constraintvalidators.bv.size._

final class SizeFunction(val annotation: Size) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Array[AnyRef])  =>
        val v = new SizeValidatorForArray
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Boolean]) =>
        val v = new SizeValidatorForArraysOfBoolean
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Byte])    =>
        val v = new SizeValidatorForArraysOfByte
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Char])    =>
        val v = new SizeValidatorForArraysOfChar
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Double])  =>
        val v = new SizeValidatorForArraysOfDouble
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Float])   =>
        val v = new SizeValidatorForArraysOfFloat
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Int])     =>
        val v = new SizeValidatorForArraysOfInt
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Long])    =>
        val v = new SizeValidatorForArraysOfLong
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Short])   =>
        val v = new SizeValidatorForArraysOfShort
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: CharSequence)   =>
        val v = new SizeValidatorForCharSequence
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x)                 =>
        // collection or seq
        x match {
          case x: util.Collection[_]                 =>
            val v = new SizeValidatorForCollection
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: scala.collection.Map[_, _]         =>
            val v = new SizeValidatorForMap
            v.initialize(annotation)
            v.isValid(x.asJava, ctx)
          case x: scala.collection.Seq[_] @unchecked =>
            val v = new SizeValidatorForCollection
            v.initialize(annotation)
            v.isValid(x.toSeq.asJava, ctx)
          case _                                     => throw new IllegalStateException("oops.")
        }
      case None                    =>
        true
    }
}
