package bitlap.validation

import java.lang.reflect.Method

import scala.jdk.CollectionConverters._

import jakarta.validation.ConstraintViolation

package object extension {

  lazy val Validator: GenericScalaValidator[Identity] = ScalaValidatorFactory.scalaValidator(new ScalaClockProvider)

  implicit final class ValidationExt(val genericValidator: GenericScalaValidator[Identity]) extends AnyVal {

    def checkObjectBinding[T](obj: T, groups: Class[_]*): List[ConstraintViolation[T]] =
      genericValidator
        .validate(obj, groups: _*)
        .toList

    def checkObject[T](obj: T, groups: Class[_]*): Identity[Boolean] = {
      val errors = genericValidator
        .validate(obj, groups: _*)
        .map { violation =>
          val path = violation.getPropertyPath.iterator().asScala.toList.map(_.getName).mkString("#")
          (path, violation.getMessage, violation.getInvalidValue)
        }
        .toList
      if (errors.nonEmpty) {
        throw new IllegalArgumentException(
          errors.headOption
            .map(pathMessageValue =>
              s"""Illegal argument ${pathMessageValue._3}, ${pathMessageValue._1} ${pathMessageValue._2}"""
            )
            .getOrElse("Illegal argument")
        )
      } else true
    }

    def checkMethodArgsBinding[T](
      obj: T,
      method: Method,
      parameterValues: Array[AnyRef],
      groups: Class[_]*
    ): List[ConstraintViolation[T]] =
      genericValidator
        .validateParameters(obj, method, parameterValues, groups: _*)
        .toList

    def checkMethodArgs[T](
      obj: T,
      method: Method,
      parameterValues: Array[AnyRef],
      groups: Class[_]*
    ): Identity[Boolean] = {
      val errors = genericValidator
        .validateParameters(obj, method, parameterValues, groups: _*)
        .map { violation =>
          (
            violation.getPropertyPath.iterator().asScala.toList.map(_.getName).mkString("#"),
            violation.getMessage,
            violation.getInvalidValue
          )
        }
        .toList
      if (errors.nonEmpty) {
        throw new IllegalArgumentException(
          errors.headOption
            .map(pathMessageValue =>
              s"""Illegal argument ${pathMessageValue._3}, ${pathMessageValue._1} ${pathMessageValue._2}"""
            )
            .getOrElse("Illegal argument")
        )
      } else true
    }
  }
}
