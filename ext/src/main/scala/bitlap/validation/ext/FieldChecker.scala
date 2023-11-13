package bitlap
package validation
package ext

trait FieldChecker {
  self =>
  @inline def check(): Boolean = Validator.validate(self)

  check()

}
