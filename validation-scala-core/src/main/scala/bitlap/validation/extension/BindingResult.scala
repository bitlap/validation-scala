package bitlap.validation.extension

import scala.collection.mutable.ListBuffer

import jakarta.validation.ConstraintViolation

final case class BindingResult(
  var hasErrors: Boolean = false,
  violations: ListBuffer[ConstraintViolation[_]] = ListBuffer.empty
)

object BindingResult {
  lazy val default: BindingResult = BindingResult()
}
