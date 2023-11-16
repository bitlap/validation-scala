package bitlap.validation

import java.lang.reflect.{ Constructor, Method }

import scala.jdk.CollectionConverters._

import jakarta.validation._
import jakarta.validation.executable.ExecutableValidator
import jakarta.validation.metadata.BeanDescriptor

final class ScalaValidator(validator: Validator) extends GenericScalaValidator[Identity] {

  private lazy val forExecutables: ExecutableValidator = validator.forExecutables()

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

  def validateParameters[T](
    obj: T,
    method: Method,
    parameterValues: Array[AnyRef],
    groups: Class[_]*
  ): Identity[Set[ConstraintViolation[T]]] =
    forExecutables.validateParameters(obj, method, parameterValues, groups: _*).asScala.toSet

  def validateReturnValue[T](
    obj: T,
    method: Method,
    returnValue: Any,
    groups: Class[_]*
  ): Identity[Set[ConstraintViolation[T]]] =
    forExecutables.validateReturnValue(obj, method, returnValue, groups: _*).asScala.toSet

  def validateConstructorParameters[T](
    constructor: Constructor[T],
    parameterValues: Array[AnyRef],
    groups: Class[_]*
  ): Identity[Set[ConstraintViolation[T]]] =
    forExecutables.validateConstructorParameters(constructor, parameterValues, groups: _*).asScala.toSet

  def validateConstructorReturnValue[T](
    constructor: Constructor[T],
    createdObject: T,
    groups: Class[_]*
  ): Identity[Set[ConstraintViolation[T]]] =
    forExecutables.validateConstructorReturnValue(constructor, createdObject, groups: _*).asScala.toSet

}
