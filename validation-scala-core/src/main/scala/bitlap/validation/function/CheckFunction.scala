package bitlap.validation.function

import jakarta.validation.ConstraintValidatorContext

trait CheckFunction[-I, +O] {
  def check(value: I): ConstraintValidatorContext => O
}
