package bitlap
package validation
package ext

trait FieldChecker {
  self =>
  Validator.validate(self)

}
