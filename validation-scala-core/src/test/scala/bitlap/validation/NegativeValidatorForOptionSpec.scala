package bitlap.validation

import scala.annotation.meta.field

import jakarta.validation.constraints.Negative

class NegativeValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionInt(
    @(Negative @field)
    name: Option[Int]
  )

  private[this] case class TestBeanWithOptionInteger(
    @(Negative @field)
    name: Option[Integer]
  )

  private[this] case class TestBeanWithOptionBigDecimal(
    @(Negative @field)
    name: Option[BigDecimal]
  )

  Seq(
    (TestBeanWithOptionInt(Some(0)), 1),
    (TestBeanWithOptionInt(Some(-1)), 0),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(0))), 1),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(-1))), 0),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("0"))), 1),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("-1"))), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
