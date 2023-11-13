package bitlap.validation

import java.time.Clock

import scala.jdk.CollectionConverters._

import jakarta.validation._
import jakarta.validation.executable.ExecutableValidator
import jakarta.validation.metadata.BeanDescriptor
import zio.{ Clock => _, _ }

/**
 * ValidatorFactory for zio.
 */
object ZioValidatorFactory {

  /**
   * Provide a ValidatorFactory with scala extensions.
   */
  def validatorFactory(clock: Clock = Clock.systemUTC()): ValidatorFactory =
    ScalaValidatorFactory.validatorFactory(clock)

  /**
   * Provide a Validator.
   */
  def zioValidator(clock: Clock = Clock.systemUTC()): GenericeScalaValidator[Task] = {
    val validator = validatorFactory(clock).getValidator

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
