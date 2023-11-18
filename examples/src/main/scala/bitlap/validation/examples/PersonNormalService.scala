package bitlap.validation.examples

import bitlap.validation.extension.*
import jakarta.validation.Valid
import jakarta.validation.constraints.{ NotBlank, NotNull }

final class PersonNormalService {

  // cascade
  @Validated
  def validatedTwoParams(@Valid person1: Person, @Valid person2: Person): String =
    s"${person1.name} - $person2"

  // cascade
  @Validated
  def validatedOneParam(@Valid person1: Person): String =
    s"${person1.name}"

  // cascade
  @ValidBinding
  def validatedBindParams(@Valid person1: Person, bindingError: BindingResult = BindingResult.default): String =
    s"${person1.name} - violationsCount: ${bindingError.violations.map(_.getMessage)}"

  @Validated
  def validatedNotNullParam(@NotNull person1: Person): String =
    s"${person1.name}"

  @Validated
  def validatedNotEmptyParam(@NotBlank name: String): String =
    s"$name"
}
