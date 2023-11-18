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
  private val personService                                       = new PersonZioService

  private val illegalPerson = Person(Some(""))

  println(callMethod(personService.zioValidatedOneParam(illegalPerson)))

  println(callMethod(personService.zioValidatedTwoParams(illegalPerson, illegalPerson)))

  println {
    Unsafe.unsafe { implicit runtime =>
      zio.Runtime.default.unsafe.run(personService.zioValidatedBindParam(illegalPerson)).getOrThrow()
    }
  }

  println(callMethod(personService.zioValidatedNotNullParam(null)))

  println(callMethod(personService.zioValidatedNotEmptyParam("")))

}
