package bitlap.validation

final case class ValidationException(msg: String) extends RuntimeException(msg)
