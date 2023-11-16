package bitlap.validation

import jakarta.validation.executable.ExecutableValidator

import java.time.{Clock, ZoneId}
import org.joda.time.{DateTime, Instant}
import org.specs2.mutable.Specification

/**
 * Base of all specs.
 */
trait BaseSpec extends Specification {

  val nowStr    = "2023-11-12T05:21:33.00Z"
  val futureStr = "2023-11-13T05:21:33.00Z"
  val pastStr   = "2023-11-11T05:21:33.00Z"
  val now       = java.time.Instant.parse((nowStr))
  val yesterday = java.time.Instant.parse((pastStr))
  val tomorrow  = java.time.Instant.parse((futureStr))

  val yesterdayDateTime = DateTime.parse(pastStr)
  val tomorrowDateTime  = DateTime.parse(futureStr)
  val nowDateTime       = DateTime.parse(nowStr)

  val nowJodaInstant       = Instant.parse((nowStr))
  val yesterdayJodaInstant = Instant.parse(pastStr)

  val fixClock  = new ScalaClockProvider(Clock.fixed(now, ZoneId.systemDefault()))
  val validator = ScalaValidatorFactory.scalaValidator(fixClock)

  def targetClassName =
    this.getClass.getSimpleName.replaceAll("Spec$", "")

  def test(bean: Any, expected: Int) = {
    val violations = validator.validate(bean)
    violations.size must_=== expected
  }

}
