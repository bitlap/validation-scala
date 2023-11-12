package bitlap.validation

import org.hibernate.validator.constraintvalidation._

import bitlap.validation.function.TimePastFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Past
import jakarta.validation.metadata.ConstraintDescriptor

/**
 * Check that the wrapped Calendar, Date, Instant and JodaTime classes passed to be validated is in the past.
 */
class PastValidatorForOption extends HibernateConstraintValidator[Past, Option[_]] {

  private var function: TimePastFunction = _

  override def initialize(
    constraintDescriptor: ConstraintDescriptor[Past],
    initializationContext: HibernateConstraintValidatorInitializationContext
  ): Unit =
    function = new TimePastFunction(constraintDescriptor, initializationContext)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
