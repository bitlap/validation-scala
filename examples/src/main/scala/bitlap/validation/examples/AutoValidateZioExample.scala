package bitlap.validation.examples

import bitlap.validation.extension.*
import zio._

object AutoValidateZioExample extends App {

  private def callMethod(a: => ZIO[Any, Nothing, String]): String =
    try
      Unsafe.unsafe { implicit runtime =>
        zio.Runtime.default.unsafe.run(a).getOrThrow()
      }
    catch {
      case e: IllegalArgumentException => e.getMessage
    }

  private def testValidatedTwoParams(
    @Validated person1: Person,
    @Validated person2: Person
  ): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"${person1.name} - $person2")

  private def testValidatedOneParams(@Validated person1: Person): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"${person1.name}")

  private def testValidatedBindParams(
    @Validated person1: Person,
    bindingError: BindingResult = BindingResult.default
  ): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"${person1.name} - ${bindingError.violations.toList}")

  private val illegalPerson = Person(Some(""))

  println(callMethod(testValidatedTwoParams(illegalPerson, illegalPerson)))

  println(callMethod(testValidatedOneParams(illegalPerson)))

  println {
    Unsafe.unsafe { implicit runtime =>
      zio.Runtime.default.unsafe.run(testValidatedBindParams(illegalPerson)).getOrThrow()
    }
  }

}
