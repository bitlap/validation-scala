package bitlap.validation.function

import javax.validation.ConstraintValidatorContext

trait CheckFunction[-I, +O] {
  def check(value: I): ConstraintValidatorContext => O
}
