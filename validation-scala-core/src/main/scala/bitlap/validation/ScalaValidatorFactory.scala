package bitlap.validation

import java.lang.reflect.{ Constructor, Method }

import scala.jdk.CollectionConverters._

import org.hibernate.validator.internal.engine.ConfigurationImpl

import jakarta.validation._
import jakarta.validation.metadata.BeanDescriptor

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
      .getterPropertySelectionStrategy(new ScalaGetterPropertySelectionStrategy)
      .clockProvider(clock)
      .buildValidatorFactory()

    validatorFactory
  }

  /**
   * Provide a Validator.
   */
  def scalaValidator(clock: ClockProvider): GenericScalaValidator[Identity] = {
    val validator           = validatorFactory(clock).getValidator
    lazy val forExecutables = validator.forExecutables()

    new GenericScalaValidator[Identity] {

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

      def getConstraintsForClass(clazz: Class[_]): Identity[BeanDescriptor] =
        validator.getConstraintsForClass(clazz)

      override def validateParameters[T](
        obj: T,
        method: Method,
        parameterValues: Array[AnyRef],
        groups: Class[_]*
      ): Identity[Set[ConstraintViolation[T]]] =
        forExecutables.validateParameters(obj, method, parameterValues, groups: _*).asScala.toSet

      override def validateReturnValue[T](
        obj: T,
        method: Method,
        returnValue: Any,
        groups: Class[_]*
      ): Identity[Set[ConstraintViolation[T]]] =
        forExecutables.validateReturnValue(obj, method, returnValue, groups: _*).asScala.toSet

      override def validateConstructorParameters[T](
        constructor: Constructor[T],
        parameterValues: Array[AnyRef],
        groups: Class[_]*
      ): Identity[Set[ConstraintViolation[T]]] =
        forExecutables.validateConstructorParameters(constructor, parameterValues, groups: _*).asScala.toSet

      override def validateConstructorReturnValue[T](
        constructor: Constructor[T],
        createdObject: T,
        groups: Class[_]*
      ): Identity[Set[ConstraintViolation[T]]] =
        forExecutables.validateConstructorReturnValue(constructor, createdObject, groups: _*).asScala.toSet
    }
  }

}
