package bitlap.validation

import javax.validation._
import javax.validation.executable.ExecutableValidator
import javax.validation.metadata.BeanDescriptor

import scala.jdk.CollectionConverters._

import zio._

/**
 * ValidatorFactory for zio.
 */
object ZioValidatorFactory {

  /**
   * Provide a ValidatorFactory with scala extensions.
   */
  lazy val validatorFactory: ValidatorFactory = ScalaValidatorFactory.validatorFactory

  /**
   * Provide a Validator.
   */
  lazy val zioValidator: GenericeScalaValidator[Task] = {
    val validator = validatorFactory.getValidator

    new GenericeScalaValidator[Task] {
      def validate[T](obj: T, groups: Class[_]*): Task[Set[ConstraintViolation[T]]] =
        ZIO.attempt(validator.validate(obj, groups: _*).asScala.toSet)

      def validateValue[T](
        beanType: Class[T],
        propertyName: String,
        value: scala.Any,
        groups: Class[_]*
      ): Task[Set[ConstraintViolation[T]]] =
        ZIO.attempt(validator.validateValue(beanType, propertyName, value, groups: _*).asScala.toSet)

      def validateProperty[T](obj: T, propertyName: String, groups: Class[_]*): Task[Set[ConstraintViolation[T]]] =
        ZIO.attempt(validator.validateProperty(obj, propertyName, groups: _*).asScala.toSet)

      def unwrap[T](t: Class[T]): Task[T] =
        ZIO.attempt(validator.unwrap(t))

      def forExecutables(): Task[ExecutableValidator] =
        ZIO.attempt(validator.forExecutables())

      def getConstraintsForClass(clazz: Class[_]): Task[BeanDescriptor] =
        ZIO.attempt(validator.getConstraintsForClass(clazz))
    }
  }

}
