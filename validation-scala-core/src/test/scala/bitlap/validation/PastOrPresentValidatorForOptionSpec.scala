package bitlap.validation

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util._

import scala.annotation.meta.field

import org.joda.time.DateTime

import jakarta.validation.constraints.PastOrPresent

class PastOrPresentValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionCalendar(
    @(PastOrPresent @field)
    value: Option[Calendar]
  )

  private[this] case class TestBeanWithOptionDate(
    @(PastOrPresent @field)
    value: Option[Date]
  )

  private[this] case class TestBeanWithOptionDateTime(
    @(PastOrPresent @field)
    value: Option[DateTime]
  )

  private[this] case class TestBeanWithOptionInstant(
    @(PastOrPresent @field)
    value: Option[Instant]
  )

  Seq(
    (TestBeanWithOptionCalendar(Some(tomorrowDateTime.toCalendar(Locale.getDefault))), 1),
    (TestBeanWithOptionCalendar(Some(nowDateTime.toCalendar(Locale.getDefault))), 0),
    (TestBeanWithOptionDate(Some(tomorrowDateTime.toDate)), 1),
    (TestBeanWithOptionDate(Some(nowJodaInstant.toDate)), 0),
    (TestBeanWithOptionDateTime(Some(tomorrowDateTime)), 1),
    (TestBeanWithOptionDateTime(Some(nowDateTime)), 0),
    (TestBeanWithOptionInstant(Some(tomorrow)), 1),
    (TestBeanWithOptionInstant(Some(now)), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
