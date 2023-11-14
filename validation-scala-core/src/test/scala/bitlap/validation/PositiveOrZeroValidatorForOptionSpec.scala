package bitlap.validation

import scala.annotation.meta.field

import jakarta.validation.constraints.PositiveOrZero

class PositiveOrZeroValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionInt(
    @(PositiveOrZero @field)
    name: Option[Int]
  )

  private[this] case class TestBeanWithOptionInteger(
    @(PositiveOrZero @field)
    name: Option[Integer]
  )

  private[this] case class TestBeanWithOptionBigDecimal(
    @(PositiveOrZero @field)
    name: Option[BigDecimal]
  )

  Seq(
    (TestBeanWithOptionInt(Some(-1)), 1),
    (TestBeanWithOptionInt(Some(0)), 0),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(-1))), 1),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(0))), 0),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("-1"))), 1),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("0"))), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
