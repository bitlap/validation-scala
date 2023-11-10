package bitlap.validation

import javax.validation.{ ConstraintValidator, ConstraintValidatorContext }
import javax.validation.constraints.Size

import scala.annotation.nowarn
import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.constraintvalidators.bv.size.{ SizeValidatorForMap => JSizeValidatorForMap }

/**
 * Check that the length of a wrapped value is between min and max.
 */
@nowarn
class SizeValidatorForMap extends ConstraintValidator[Size, scala.collection.Map[_, _]] {
  private var constraintAnnotation: Size = _

  override def initialize(constraintAnnotation: Size): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: scala.collection.Map[_, _], context: ConstraintValidatorContext): Boolean = {
    val v = new JSizeValidatorForMap
    v.initialize(constraintAnnotation)
    v.isValid(value.toMap.asJava, context)
  }
}
