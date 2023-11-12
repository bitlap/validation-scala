package bitlap.validation

import org.hibernate.validator.constraintvalidation._

import bitlap.validation.function.TimePastOrPresentFunction
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.metadata.ConstraintDescriptor

/**
 * Check that the wrapped Calendar, Date, Instant and JodaTime classes passed to be validated is in the past or in the
 * present.
 */
class PastOrPresentValidatorForOption extends HibernateConstraintValidator[PastOrPresent, Option[_]] {

  private var function: TimePastOrPresentFunction = _

  override def initialize(
    constraintDescriptor: ConstraintDescriptor[PastOrPresent],
    initializationContext: HibernateConstraintValidatorInitializationContext
  ): Unit =
    function = new TimePastOrPresentFunction(constraintDescriptor, initializationContext)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
