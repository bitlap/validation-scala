package bitlap.validation

import java.lang.reflect.Method

import scala.jdk.CollectionConverters._

import jakarta.validation.ConstraintViolation
import zio._

package object ext {

  lazy val ZioValidator: GenericScalaValidator[Task] = new ZioScalaValidator(
    ScalaValidatorFactory.validatorFactory(new ScalaClockProvider()).getValidator
  )

  implicit final class ValidationExtZio(val genericValidator: GenericScalaValidator[Task]) extends AnyVal {

    def checkMethodArgsBinding[T](
      obj: T,
      method: Method,
      parameterValues: Array[AnyRef],
      groups: Class[_]*
    ): Task[List[ConstraintViolation[T]]] =
      genericValidator
        .validateParameters(obj, method, parameterValues, groups: _*)
        .map(_.toList)

    def checkMethodArgs[T](
      obj: T,
      method: Method,
      parameterValues: Array[AnyRef],
      groups: Class[_]*
    ): Task[Boolean] =
      genericValidator
        .validateParameters(obj, method, parameterValues, groups: _*)
        .flatMap { vs =>

          val errors = vs.headOption.map { violation =>
            val path = violation.getPropertyPath.iterator().asScala.toList.map(_.getName).mkString("#")
            (path, violation.getMessage, violation.getInvalidValue)
          }

          if (errors.nonEmpty) {
            ZIO.fail(
              new IllegalArgumentException(
                errors
                  .map(pathMessageValue =>
                    s"""Illegal argument ${pathMessageValue._3}, ${pathMessageValue._1} ${pathMessageValue._2}"""
                  )
                  .getOrElse("Illegal argument")
              )
            )
          } else ZIO.succeed(true)
        }

  }
}
