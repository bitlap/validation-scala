package bitlap.validation

import scala.annotation.meta.field

import jakarta.validation.constraints.NegativeOrZero

class NegativeOrZeroValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionInt(
    @(NegativeOrZero @field)
    name: Option[Int]
  )

  private[this] case class TestBeanWithOptionInteger(
    @(NegativeOrZero @field)
    name: Option[Integer]
  )

  private[this] case class TestBeanWithOptionBigDecimal(
    @(NegativeOrZero @field)
    name: Option[BigDecimal]
  )

  Seq(
    (TestBeanWithOptionInt(Some(1)), 1),
    (TestBeanWithOptionInt(Some(-1)), 0),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(1))), 1),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(-1))), 0),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("1"))), 1),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("-1"))), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
