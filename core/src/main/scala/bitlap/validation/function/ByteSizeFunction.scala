package bitlap.validation.function

import java.io.UnsupportedEncodingException
import javax.validation.ConstraintValidatorContext

import org.hibernate.validator.internal.util.logging.{ Log, LoggerFactory }

import bitlap.validation.ByteSize

final class ByteSizeFunction(min: => Int, max: => Int, charsetName: => String) extends CheckFunction[Any, Boolean] {

  private val log: Log = LoggerFactory.make

  override def check(value: Any): ConstraintValidatorContext => Boolean = { ctx =>
    value match {
      case null | None     =>
        true
      case Some(x: String) =>
        val length = x.getBytes(charsetName).length
        length >= min && length <= max
      case x: String       =>
        val length = x.getBytes(charsetName).length
        length >= min && length <= max
      case _               =>
        throw new IllegalStateException("oops.")
    }
  }

  def validateParameters(): Unit = {
    if (min < 0) {
      throw log.getMinCannotBeNegativeException
    }
    if (max < 0) {
      throw log.getMaxCannotBeNegativeException
    }
    if (max < min) {
      throw log.getLengthCannotBeNegativeException
    }

    try {
      "a".getBytes(charsetName)
      ()
    } catch {
      case e: UnsupportedEncodingException =>
        throw new IllegalArgumentException("Invalid charset name", e)
    }
  }
}
