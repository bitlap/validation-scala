package bitlap.validation

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.{ Calendar, Date, Locale }
import java.util.concurrent.TimeUnit
import javax.validation.constraints.Future

import scala.annotation.meta.field

import org.joda.time.DateTime

class FutureValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionCalendar(
    @(Future @field)
    value: Option[Calendar]
  )

  private[this] case class TestBeanWithOptionDate(
    @(Future @field)
    value: Option[Date]
  )

  private[this] case class TestBeanWithOptionDateTime(
    @(Future @field)
    value: Option[DateTime]
  )

  private[this] case class TestBeanWithOptionInstant(
    @(Future @field)
    value: Option[Instant]
  )

  val yesterday = DateTime.now().minusDays(1)
  val tomorrow  = DateTime.now().plusDays(1)

  val yesterdayInstant = java.time.Instant.now().minus(1, ChronoUnit.DAYS)
  val tomorrowInstant  = java.time.Instant.now().plus(1, ChronoUnit.DAYS)

  Seq(
    (TestBeanWithOptionCalendar(Some(yesterday.toCalendar(Locale.getDefault))), 1),
    (TestBeanWithOptionCalendar(Some(tomorrow.toCalendar(Locale.getDefault))), 0),
    (TestBeanWithOptionDate(Some(yesterday.toDate)), 1),
    (TestBeanWithOptionDate(Some(tomorrow.toDate)), 0),
    (TestBeanWithOptionDateTime(Some(yesterday)), 1),
    (TestBeanWithOptionDateTime(Some(tomorrow)), 0),
    (TestBeanWithOptionInstant(Some(yesterdayInstant)), 1),
    (TestBeanWithOptionInstant(Some(tomorrowInstant)), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
