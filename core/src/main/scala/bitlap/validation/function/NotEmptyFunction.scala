package bitlap.validation.function

import java.util

import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator
import org.hibernate.validator.internal.constraintvalidators.bv.size._

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.NotEmpty

final case class NotEmptyFunction(annotation: NotEmpty) extends CheckOptionFunction {

  override def check(value: Option[_]): ConstraintValidatorContext => Boolean = ctx =>
    value match {
      case Some(x: Array[AnyRef])  =>
        x.size > 0
      case Some(x: Array[Boolean]) =>
        x.size > 0
      case Some(x: Array[Byte])    =>
        x.size > 0
      case Some(x: Array[Char])    =>
        x.size > 0
      case Some(x: Array[Double])  =>
        x.size > 0
      case Some(x: Array[Float])   =>
        x.size > 0
      case Some(x: Array[Int])     =>
        x.size > 0
      case Some(x: Array[Long])    =>
        x.size > 0
      case Some(x: Array[Short])   =>
        x.size > 0
      case Some(x: CharSequence)   =>
        x.length() > 0
      case Some(x)                 =>
        // collection or seq
        x match {
          case x: util.Collection[_]                 =>
            x.size() > 0
          case x: util.Map[_, _]                     =>
            x.size() > 0
          case x: scala.collection.Map[_, _]         =>
            x.size > 0
          case x: scala.collection.Seq[_] @unchecked =>
            x.size > 0
          case x: scala.collection.Set[_] @unchecked =>
            x.size > 0
          case _                                     => throw new IllegalStateException("oops.")
        }
        throw new IllegalStateException("oops.")
      case None                    =>
        true
    }
}
