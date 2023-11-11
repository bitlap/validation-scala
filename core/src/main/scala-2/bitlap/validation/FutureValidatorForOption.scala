package bitlap.validation

import org.hibernate.validator.constraintvalidation._

import bitlap.validation.function.TimeFutureFunction
import jakarta.validation.{ ConstraintValidator, ConstraintValidatorContext }
import jakarta.validation.constraints.Future
import jakarta.validation.metadata.ConstraintDescriptor

/**
 * Check that the wrapped Calendar, Date and JodaTime classes passed to be validated is in the future.
 */
class FutureValidatorForOption extends HibernateConstraintValidator[Future, Option[_]] {

  private var function: TimeFutureFunction = _

  override def initialize(
    constraintDescriptor: ConstraintDescriptor[Future],
    initializationContext: HibernateConstraintValidatorInitializationContext
  ): Unit =
    function = new TimeFutureFunction(constraintDescriptor, initializationContext)

  override def isValid(value: Option[_], context: ConstraintValidatorContext): Boolean =
    function.check(value)(context)
}
