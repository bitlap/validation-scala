package bitlap.validation

import scala.annotation.nowarn
import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.constraintvalidators.bv.size.SizeValidatorForCollection

import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Size

/**
 * Check that the length of a wrapped value is between min and max.
 */
@nowarn
class SizeValidatorForSet extends ConstraintValidator[Size, scala.collection.Set[_]] {
  private var constraintAnnotation: Size = _

  override def initialize(constraintAnnotation: Size): Unit =
    this.constraintAnnotation = constraintAnnotation

  override def isValid(value: scala.collection.Set[_], context: ConstraintValidatorContext): Boolean = {
    val v = new SizeValidatorForCollection
    v.initialize(constraintAnnotation)
    v.isValid(value.toSet.asJava, context)
  }
}
