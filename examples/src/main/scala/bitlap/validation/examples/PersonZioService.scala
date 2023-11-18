package bitlap.validation.examples

import bitlap.validation.extension.*
import jakarta.validation.Valid
import jakarta.validation.constraints.{ NotBlank, NotNull }
import zio.*

final class PersonZioService {

  // cascade
  @Validated
  def zioValidatedTwoParams(
    @Valid person1: Person,
    @Valid person2: Person
  ): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"${person1.name} - $person2")

  // cascade
  @Validated
  def zioValidatedOneParam(@Valid person1: Person): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"${person1.name}")

  // cascade
  @ValidBinding
  def zioValidatedBindParam(
    @Valid person1: Person,
    bindingError: BindingResult = BindingResult.default
  ): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"${person1.name} - violationsCount: ${bindingError.violations.map(_.getMessage)}")

  @Validated
  def zioValidatedNotNullParam(@NotNull person1: Person): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"${person1.name}")

  @Validated
  def zioValidatedNotEmptyParam(@NotBlank name: String): ZIO[Any, Nothing, String] =
    ZIO.succeed(s"$name")

}
