package bitlap.validation.examples

import scala.annotation.meta._

import bitlap.validation.extension.*
import bitlap.validation.extension.BindingResult
import jakarta.validation.Valid

object AutoValidateNormalExample extends App {

  private def callMethod(a: => String): String =
    try a
    catch case e: IllegalArgumentException => e.getMessage

  private def testValidatedTwoParams(@Validated person1: Person, @Validated person2: Person): String =
    s"${person1.name} - $person2"

  private def testValidatedOneParams(@Validated person1: Person): String =
    s"${person1.name}"

  private def testValidatedBindParams(
    @Validated person1: Person,
    bindingError: BindingResult = BindingResult.default
  ): String =
    s"${person1.name} - ${bindingError.violations.toList}"

  private val illegalPerson = Person(Some(""))

  println(callMethod(testValidatedTwoParams(illegalPerson, illegalPerson)))

  println(callMethod(testValidatedOneParams(illegalPerson)))

  println(testValidatedBindParams(illegalPerson))

}
