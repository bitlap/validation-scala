package bitlap.validation

import java.util
import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Size

import scala.collection.immutable._

import org.hibernate.validator.internal.constraintvalidators.bv.size._

import bitlap.validation.{ SizeValidatorForMap => ScalaSizeValidatorForMap }
import bitlap.validation.Utils._

/**
 * Check that the length of a wrapped value is between min and max.
 */
class SizeValidatorForOption extends ConstraintValidator[Size, IterableOnce[_]] {

  private var constraintAnnotation: Size = _

  override def initialize(constraintAnnotation: Size): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value) {
      case Some(x: Array[AnyRef])  =>
        val v = new SizeValidatorForArray
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Boolean]) =>
        val v = new SizeValidatorForArraysOfBoolean
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Byte])    =>
        val v = new SizeValidatorForArraysOfByte
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Char])    =>
        val v = new SizeValidatorForArraysOfChar
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Double])  =>
        val v = new SizeValidatorForArraysOfDouble
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Float])   =>
        val v = new SizeValidatorForArraysOfFloat
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Int])     =>
        val v = new SizeValidatorForArraysOfInt
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Long])    =>
        val v = new SizeValidatorForArraysOfLong
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: Array[Short])   =>
        val v = new SizeValidatorForArraysOfShort
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x: CharSequence)   =>
        val v = new SizeValidatorForCharSequence
        v.initialize(constraintAnnotation)
        v.isValid(x, context)
      case Some(x)                 =>
        // collection or seq
        x match {
          case x: util.Collection[_] =>
            val v = new SizeValidatorForCollection
            v.initialize(constraintAnnotation)
            v.isValid(x, context)
          case x                     => handleScalaCollection(x, context)
        }
      case None                    =>
        true
    }

  private def handleScalaCollection(x: Any, context: ConstraintValidatorContext): Boolean =
    x match {
      case x: scala.collection.Seq[_]    =>
        val v = new SizeValidatorForSeq
        v.initialize(constraintAnnotation)
        v.isValid(x.toSeq, context)
      case x: scala.collection.Map[_, _] =>
        val v = new ScalaSizeValidatorForMap
        v.initialize(constraintAnnotation)
        v.isValid(x.toMap, context)
      case _                             => true
    }

}
