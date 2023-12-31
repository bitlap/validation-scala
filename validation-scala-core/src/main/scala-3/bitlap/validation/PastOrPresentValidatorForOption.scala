package bitlap.validation

import org.hibernate.validator.constraintvalidation._

import bitlap.validation.Utils._
import bitlap.validation.function.TimePastOrPresentFunction
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.metadata.ConstraintDescriptor

/**
 * Check that the wrapped Calendar, Date, Instant and JodaTime classes passed to be validated is in the past or in the
 * present.
 */
class PastOrPresentValidatorForOption extends HibernateConstraintValidator[PastOrPresent, IterableOnce[_]] {

  private var function: TimePastOrPresentFunction = _

  override def initialize(
    constraintDescriptor: ConstraintDescriptor[PastOrPresent],
    initializationContext: HibernateConstraintValidatorInitializationContext
  ): Unit =
    function = TimePastOrPresentFunction(constraintDescriptor, initializationContext)

  override def isValid(value: IterableOnce[_], context: ConstraintValidatorContext): Boolean =
    checkForOption(value)(opt => function.check(opt)(context))
}
