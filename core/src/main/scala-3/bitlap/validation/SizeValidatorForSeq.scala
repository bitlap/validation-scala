package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Size

import scala.annotation.nowarn
import scala.collection.immutable.{ Seq => _ }
import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCollection

/**
 * Check that the length of a wrapped value is between min and max.
 */
@nowarn
class SizeValidatorForSeq extends ConstraintValidator[Size, scala.collection.Seq[_]] {
  private var constraintAnnotation: Size = _

  override def initialize(constraintAnnotation: Size): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: scala.collection.Seq[_], context: ConstraintValidatorContext): Boolean = {
    val v = new SizeValidatorForCollection
    v.initialize(constraintAnnotation)
    v.isValid(value.toSeq.asJava, context)
  }
}
