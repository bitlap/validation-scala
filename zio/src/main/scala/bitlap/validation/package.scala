package bitlap

import jakarta.validation._
import zio.{ Task, ZIO }

package object validation {

  lazy val zioValidator: GenericeScalaValidator[Task] = ZioValidatorFactory.zioValidator()

  implicit final class ValidationExt(val genericValidator: GenericeScalaValidator[Task]) extends AnyVal {

    def checkArgument[T](obj: T, groups: Class[_]*): ZIO[Any, Throwable, Unit] =
      for {
        constraintViolations <- genericValidator.validate(obj)
        errors               <-
          ZIO.attempt(
            constraintViolations
              .map(violation => (violation.getPropertyPath.toString, violation.getMessage, violation.getInvalidValue))
              .toList
          )
        res                  <- ZIO
                                  .when(errors.nonEmpty) {
                                    ZIO.fail(
                                      new IllegalArgumentException(
                                        errors
                                          .map(pathMessageValue =>
                                            s"""${pathMessageValue._1}=${pathMessageValue._3}, error=${pathMessageValue._2}"""
                                          )
                                          .mkString(" and ")
                                      )
                                    )
                                  }
                                  .someOrElse(())
      } yield res
  }
}
