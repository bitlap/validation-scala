package bitlap.validation.ext

import java.lang.reflect.{ Constructor, Method }

import scala.jdk.CollectionConverters._

import bitlap.validation.GenericScalaValidator
import jakarta.validation.{ ConstraintViolation, Validator }
import jakarta.validation.metadata.BeanDescriptor
import zio._

final class ZioScalaValidator(validator: Validator) extends GenericScalaValidator[Task] {

  private lazy val forExecutables = validator.forExecutables()

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

  def getConstraintsForClass(clazz: Class[_]): Task[BeanDescriptor] =
    ZIO.attempt(validator.getConstraintsForClass(clazz))

  def validateParameters[T](
    obj: T,
    method: Method,
    parameterValues: Array[AnyRef],
    groups: Class[_]*
  ): Task[Set[ConstraintViolation[T]]] =
    ZIO.attempt(forExecutables.validateParameters(obj, method, parameterValues, groups: _*).asScala.toSet)

  def validateReturnValue[T](
    obj: T,
    method: Method,
    returnValue: Any,
    groups: Class[_]*
  ): Task[Set[ConstraintViolation[T]]] =
    ZIO.attempt(forExecutables.validateReturnValue(obj, method, returnValue, groups: _*).asScala.toSet)

  def validateConstructorParameters[T](
    constructor: Constructor[T],
    parameterValues: Array[AnyRef],
    groups: Class[_]*
  ): Task[Set[ConstraintViolation[T]]] =
    ZIO.attempt(
      forExecutables.validateConstructorParameters(constructor, parameterValues, groups: _*).asScala.toSet
    )

  def validateConstructorReturnValue[T](
    constructor: Constructor[T],
    createdObject: T,
    groups: Class[_]*
  ): Task[Set[ConstraintViolation[T]]] =
    ZIO.attempt(forExecutables.validateConstructorReturnValue(constructor, createdObject, groups: _*).asScala.toSet)

}
