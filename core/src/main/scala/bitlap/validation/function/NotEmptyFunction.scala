package bitlap.validation.function

import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator
import org.hibernate.validator.internal.constraintvalidators.bv.notempty._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.NotEmpty

final case class NotEmptyFunction(annotation: NotEmpty) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Array[AnyRef])  =>
        val v = new NotEmptyValidatorForArray
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Boolean]) =>
        val v = new NotEmptyValidatorForArraysOfBoolean
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Byte])    =>
        val v = new NotEmptyValidatorForArraysOfByte
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Char])    =>
        val v = new NotEmptyValidatorForArraysOfChar
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Double])  =>
        val v = new NotEmptyValidatorForArraysOfDouble
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Float])   =>
        val v = new NotEmptyValidatorForArraysOfFloat
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Int])     =>
        val v = new NotEmptyValidatorForArraysOfInt
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Long])    =>
        val v = new NotEmptyValidatorForArraysOfLong
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: Array[Short])   =>
        val v = new NotEmptyValidatorForArraysOfShort
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x: CharSequence)   =>
        val v = new NotEmptyValidatorForCharSequence
        v.initialize(annotation)
        v.isValid(x, ctx)
      case Some(x)                 =>
        // collection or seq
        x match {
          case x: java.util.Collection[_]            =>
            val v = new NotEmptyValidatorForCollection
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: java.util.Map[_, _]                =>
            val v = new NotEmptyValidatorForMap
            v.initialize(annotation)
            v.isValid(x, ctx)
          case x: scala.collection.Map[_, _]         =>
            val v = new NotEmptyValidatorForMap
            v.initialize(annotation)
            v.isValid(x.asJava, ctx)
          case x: scala.collection.Seq[_]            =>
            val v = new NotEmptyValidatorForCollection
            v.initialize(annotation)
            v.isValid(x.asJava, ctx)
          case x: scala.collection.Set[_] @unchecked =>
            val v = new NotEmptyValidatorForCollection
            v.initialize(annotation)
            v.isValid(x.asJava, ctx)
          case _                                     => throw new IllegalStateException("oops.")
        }
      case None                    =>
        true
    }
}
