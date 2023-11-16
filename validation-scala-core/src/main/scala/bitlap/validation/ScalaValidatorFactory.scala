package bitlap.validation

import org.hibernate.validator.internal.engine.ConfigurationImpl

import jakarta.validation._

/**
 * ValidatorFactory for scala.
 */
object ScalaValidatorFactory {

  /**
   * Provide a ValidatorFactory with scala extensions.
   */
  def validatorFactory(clock: ClockProvider): ValidatorFactory = {
    val validatorFactory = Validation
      .byDefaultProvider()
      .configure()
      .asInstanceOf[ConfigurationImpl]
      .failFast(true)
      .getterPropertySelectionStrategy(new ScalaGetterPropertySelectionStrategy)
      .clockProvider(clock)
      .buildValidatorFactory()

    validatorFactory
  }

  /**
   * Provide a Validator.
   */
  def scalaValidator(clock: ClockProvider): GenericScalaValidator[Identity] =
    new ScalaValidator(validatorFactory(clock).getValidator)

}
