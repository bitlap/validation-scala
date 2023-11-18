package bitlap.validation.examples

import scala.annotation.meta._

import bitlap.validation.extension.*
import bitlap.validation.extension.BindingResult
import jakarta.validation.Valid

object AutoValidateNormalExample extends App {

  def callMethod(a: => String): String =
    try a
    catch case e: IllegalArgumentException => e.getMessage

  private val personService = new PersonNormalService
  private val illegalPerson = Person(Some(""))

  println(callMethod(personService.validatedOneParam(illegalPerson)))

  println(callMethod(personService.validatedTwoParams(illegalPerson, illegalPerson)))

  println(personService.validatedBindParams(illegalPerson))

  println(callMethod(personService.validatedNotNullParam(null)))

  println(callMethod(personService.validatedNotEmptyParam("")))

}
