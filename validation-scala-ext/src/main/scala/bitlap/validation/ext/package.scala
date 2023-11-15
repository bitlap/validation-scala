package bitlap.validation

import jakarta.validation.ConstraintViolation

package object ext {

  lazy val Validator: GenericeScalaValidator[Identity] = ScalaValidatorFactory.scalaValidator()

  implicit final class ValidationExt(val genericValidator: GenericeScalaValidator[Identity]) extends AnyVal {

    def checkArgsBinding[T](obj: T, groups: Class[_]*): List[ConstraintViolation[T]] =
      genericValidator
        .validate(obj)
        .toList

    def checkArgs[T](obj: T, groups: Class[_]*): Identity[Boolean] = {
      val errors = genericValidator
        .validate(obj)
        .map(violation => (violation.getPropertyPath.toString, violation.getMessage, violation.getInvalidValue))
        .toList
      if (errors.nonEmpty) {
        throw new IllegalArgumentException(
          errors
            .map(pathMessageValue => s"""${pathMessageValue._1}=${pathMessageValue._3}, error=${pathMessageValue._2}""")
            .mkString(" and ")
        )
      } else true
    }

  }
}
