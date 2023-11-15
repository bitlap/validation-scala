package bitlap.validation

import java.time.Instant
import java.util._

import scala.annotation.meta.field

import org.joda.time.DateTime

import jakarta.validation.constraints.Future

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

  Seq(
    (TestBeanWithOptionCalendar(Some(yesterdayDateTime.toCalendar(Locale.getDefault))), 1),
    (TestBeanWithOptionCalendar(Some(tomorrowDateTime.toCalendar(Locale.getDefault))), 0),
    (TestBeanWithOptionDate(Some(yesterdayDateTime.toDate)), 1),
    (TestBeanWithOptionDate(Some(tomorrowDateTime.toDate)), 0),
    (TestBeanWithOptionDateTime(Some(yesterdayDateTime)), 1),
    (TestBeanWithOptionDateTime(Some(tomorrowDateTime)), 0),
    (TestBeanWithOptionInstant(Some(yesterday)), 1),
    (TestBeanWithOptionInstant(Some(tomorrow)), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
