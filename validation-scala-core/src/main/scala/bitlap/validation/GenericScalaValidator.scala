package bitlap.validation

import java.lang.reflect.{ Constructor, Method }

import jakarta.validation.ConstraintViolation
import jakarta.validation.metadata.BeanDescriptor

/**
 * Validator wrapper for scala.
 *
 * @param validator
 *   delegate.
 */
trait GenericScalaValidator[F[_]] {

  def validate[T](obj: T, groups: Class[_]*): F[Set[ConstraintViolation[T]]]

  def validateValue[T](
    beanType: Class[T],
    propertyName: String,
    value: Any,
    groups: Class[_]*
  ): F[Set[ConstraintViolation[T]]]

  def validateProperty[T](obj: T, propertyName: String, groups: Class[_]*): F[Set[ConstraintViolation[T]]]

  def unwrap[T](t: Class[T]): F[T]

  def getConstraintsForClass(clazz: Class[_]): F[BeanDescriptor]

  def validateParameters[T](
    obj: T,
    method: Method,
    parameterValues: Array[AnyRef],
    groups: Class[_]*
  ): F[Set[ConstraintViolation[T]]]

  def validateReturnValue[T](
    obj: T,
    method: Method,
    returnValue: Any,
    groups: Class[_]*
  ): F[Set[ConstraintViolation[T]]]

  def validateConstructorParameters[T](
    constructor: Constructor[T],
    parameterValues: Array[AnyRef],
    groups: Class[_]*
  ): F[Set[ConstraintViolation[T]]]

  def validateConstructorReturnValue[T](
    constructor: Constructor[T],
    createdObject: T,
    groups: Class[_]*
  ): F[Set[ConstraintViolation[T]]]
}
