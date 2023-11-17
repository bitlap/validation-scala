package bitlap.validation

import scala.jdk.CollectionConverters._

import jakarta.validation.ConstraintViolation

package object extension {
  lazy val Validator: GenericScalaValidator[Identity] = ScalaValidatorFactory.scalaValidator(new ScalaClockProvider)

  implicit final class ValidationExt(val genericValidator: GenericScalaValidator[Identity]) extends AnyVal {

    def checkArgsBinding[T](obj: T, groups: Class[_]*): List[ConstraintViolation[T]] =
      genericValidator
        .validate(obj, groups: _*)
        .toList

    def checkArgs[T](obj: T, groups: Class[_]*): Identity[Boolean] = {
      val errors = genericValidator
        .validate(obj, groups: _*)
        .map { violation =>
          val path = violation.getPropertyPath.iterator().asScala.toList.map(_.getName).mkString("/")
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

  }
}
