package bitlap.validation

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.{ Calendar, Date, Locale }

import scala.annotation.meta.field

import org.joda.time.DateTime

import jakarta.validation.constraints.Past

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

  Seq(
    (TestBeanWithOptionCalendar(Some(tomorrowDateTime.toCalendar(Locale.getDefault))), 1),
    (TestBeanWithOptionCalendar(Some(yesterdayDateTime.toCalendar(Locale.getDefault))), 0),
    (TestBeanWithOptionDate(Some(tomorrowDateTime.toDate)), 1),
    (TestBeanWithOptionDate(Some(yesterdayDateTime.toDate)), 0),
    (TestBeanWithOptionDateTime(Some(tomorrowDateTime)), 1),
    (TestBeanWithOptionDateTime(Some(yesterdayDateTime)), 0),
    (TestBeanWithOptionInstant(Some(tomorrow)), 1),
    (TestBeanWithOptionInstant(Some(yesterday)), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
