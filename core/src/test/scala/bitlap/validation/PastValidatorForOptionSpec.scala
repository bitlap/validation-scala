package bitlap.validation

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.{ Calendar, Date, Locale }
import javax.validation.constraints.Past

import scala.annotation.meta.field

import org.joda.time.DateTime

class PastValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionCalendar(
    @(Past @field)
    value: Option[Calendar]
  )

  private[this] case class TestBeanWithOptionDate(
    @(Past @field)
    value: Option[Date]
  )

  private[this] case class TestBeanWithOptionDateTime(
    @(Past @field)
    value: Option[DateTime]
  )

  private[this] case class TestBeanWithOptionInstant(
    @(Past @field)
    value: Option[Instant]
  )

  val tomorrow  = DateTime.now().plusDays(1)
  val yesterday = DateTime.now().minusDays(1)

  val tomorrowInstant  = java.time.Instant.now().plus(1, ChronoUnit.DAYS)
  val yesterdayInstant = java.time.Instant.now().minus(1, ChronoUnit.DAYS)

  Seq(
    (TestBeanWithOptionCalendar(Some(tomorrow.toCalendar(Locale.getDefault))), 1),
    (TestBeanWithOptionCalendar(Some(yesterday.toCalendar(Locale.getDefault))), 0),
    (TestBeanWithOptionDate(Some(tomorrow.toDate)), 1),
    (TestBeanWithOptionDate(Some(yesterday.toDate)), 0),
    (TestBeanWithOptionDateTime(Some(tomorrow)), 1),
    (TestBeanWithOptionDateTime(Some(yesterday)), 0),
    (TestBeanWithOptionInstant(Some(tomorrowInstant)), 1),
    (TestBeanWithOptionInstant(Some(yesterdayInstant)), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
