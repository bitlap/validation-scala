package bitlap.validation

import java.time.Clock

import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.engine.ConfigurationImpl

import jakarta.validation.{ ClockProvider, ConstraintViolation, Validation, Validator, ValidatorFactory }
import jakarta.validation.executable.ExecutableValidator
import jakarta.validation.metadata.BeanDescriptor

/**
 * ValidatorFactory for scala.
 */
object ScalaValidatorFactory {

  /**
   * Provide a ValidatorFactory with scala extensions.
   */
  def validatorFactory(clock: Clock = Clock.systemDefaultZone()): ValidatorFactory = {
    val stream = getClass.getClassLoader.getResourceAsStream("scala-constraint-mapping.xml")

    val validatorFactory = Validation
      .byDefaultProvider()
      .configure()
      .addMapping(stream)
      .asInstanceOf[ConfigurationImpl]
      .clockProvider(new ClockProvider {
        override def getClock: Clock = clock
      })
      .buildValidatorFactory()

    validatorFactory
  }

  /**
   * Provide a Validator.
   */
  def scalaValidator(clock: Clock = Clock.systemDefaultZone()): GenericeScalaValidator[Identity] = {
    val validator = validatorFactory(clock).getValidator

    new GenericeScalaValidator[Identity] {

      def validate[T](obj: T, groups: Class[_]*): Identity[Set[ConstraintViolation[T]]] =
        validator.validate(obj, groups: _*).asScala.toSet

      def validateValue[T](
        beanType: Class[T],
        propertyName: String,
        value: scala.Any,
        groups: Class[_]*
      ): Identity[Set[ConstraintViolation[T]]] =
        validator.validateValue(beanType, propertyName, value, groups: _*).asScala.toSet

      def validateProperty[T](obj: T, propertyName: String, groups: Class[_]*): Identity[Set[ConstraintViolation[T]]] =
        validator.validateProperty(obj, propertyName, groups: _*).asScala.toSet

      def unwrap[T](t: Class[T]): Identity[T] =
        validator.unwrap(t)

      def forExecutables(): Identity[ExecutableValidator] =
        validator.forExecutables()

      def getConstraintsForClass(clazz: Class[_]): Identity[BeanDescriptor] =
        validator.getConstraintsForClass(clazz)
    }
  }

}
