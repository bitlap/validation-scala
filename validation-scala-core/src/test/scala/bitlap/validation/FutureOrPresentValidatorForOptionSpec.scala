package bitlap.validation

import java.time.Instant
import java.util._

import scala.annotation.meta.field

import org.joda.time.DateTime

import jakarta.validation.constraints.FutureOrPresent

class FutureOrPresentValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionCalendar(
    @(FutureOrPresent @field)
    value: Option[Calendar]
  )

  private[this] case class TestBeanWithOptionDate(
    @(FutureOrPresent @field)
    value: Option[Date]
  )

  private[this] case class TestBeanWithOptionDateTime(
    @(FutureOrPresent @field)
    value: Option[DateTime]
  )

  private[this] case class TestBeanWithOptionInstant(
    @(FutureOrPresent @field)
    value: Option[Instant]
  )

  Seq(
    (TestBeanWithOptionCalendar(Some(yesterdayDateTime.toCalendar(Locale.getDefault))), 1),
    (TestBeanWithOptionCalendar(Some(nowDateTime.toCalendar(Locale.getDefault))), 0),
    (TestBeanWithOptionDate(Some(yesterdayJodaInstant.toDate)), 1),
    (TestBeanWithOptionDate(Some(nowJodaInstant.toDate)), 0),
    (TestBeanWithOptionDateTime(Some(yesterdayDateTime)), 1),
    (TestBeanWithOptionDateTime(Some(nowDateTime)), 0),
    (TestBeanWithOptionInstant(Some(yesterday)), 1),
    (TestBeanWithOptionInstant(Some(now)), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
