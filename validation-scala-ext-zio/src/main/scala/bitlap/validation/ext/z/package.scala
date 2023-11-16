package bitlap.validation.ext

import scala.jdk.CollectionConverters._

import bitlap.validation._
import jakarta.validation.ConstraintViolation
import zio._

/**
 * @author
 *   梦境迷离
 * @version 1.0,2023/11/16
 */
package object z {

  lazy val ZioValidator: GenericScalaValidator[Task] = new ZioScalaValidator(
    ScalaValidatorFactory.validatorFactory(new ScalaClockProvider()).getValidator
  )

  implicit final class ValidationExtZio(val genericValidator: GenericScalaValidator[Task]) extends AnyVal {

    def checkArgsBinding[T](obj: T, groups: Class[_]*): Task[List[ConstraintViolation[T]]] =
      genericValidator
        .validate(obj, groups: _*)
        .map(_.toList)

    def checkArgs[T](obj: T, groups: Class[_]*): Task[Boolean] =
      genericValidator
        .validate(obj, groups: _*)
        .flatMap { vs =>

          val errors = vs.headOption.map { violation =>
            val path = violation.getPropertyPath.iterator().asScala.toList.map(_.getName).mkString("/")
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
