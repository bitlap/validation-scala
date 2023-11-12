package bitlap.validation

import org.hibernate.validator.constraintvalidation._

import bitlap.validation.function.TimeFutureOrPresentFunction
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.metadata.ConstraintDescriptor

/**
 * Check that the wrapped Calendar, Date, Instant and JodaTime classes passed to be validated is in the future or in the
 * present.
 */
class FutureOrPresentValidatorForOption extends HibernateConstraintValidator[FutureOrPresent, Option[_]] {

  private var function: TimeFutureOrPresentFunction = _

  override def initialize(
    constraintDescriptor: ConstraintDescriptor[FutureOrPresent],
    initializationContext: HibernateConstraintValidatorInitializationContext
  ): Unit =
    function = new TimeFutureOrPresentFunction(constraintDescriptor, initializationContext)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
