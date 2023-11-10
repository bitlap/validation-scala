package bitlap.validation

import javax.validation.ConstraintViolation
import javax.validation.executable.ExecutableValidator
import javax.validation.metadata.BeanDescriptor

/**
 * Validator wrapper for scala.
 *
 * @param validator
 *   delegate.
 */
trait GenericeScalaValidator[F[_]] {

  def validate[T](obj: T, groups: Class[_]*): F[Set[ConstraintViolation[T]]]

  def validateValue[T](
    beanType: Class[T],
    propertyName: String,
    value: scala.Any,
    groups: Class[_]*
  ): F[Set[ConstraintViolation[T]]]

  def validateProperty[T](obj: T, propertyName: String, groups: Class[_]*): F[Set[ConstraintViolation[T]]]

  def unwrap[T](t: Class[T]): F[T]

  def forExecutables(): F[ExecutableValidator]

  def getConstraintsForClass(clazz: Class[_]): F[BeanDescriptor]
}
