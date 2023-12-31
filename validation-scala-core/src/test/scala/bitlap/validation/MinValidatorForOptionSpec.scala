package bitlap.validation

import scala.annotation.meta.field

import jakarta.validation.constraints.Min

class MinValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionString(
    @(Min @field)(value = 10)
    value: Option[String]
  )

  private[this] case class TestBeanWithOptionInt(
    @(Min @field)(value = 10)
    value: Option[Int]
  )

  private[this] case class TestBeanWithOptionDouble(
    @(Min @field)(value = 10)
    value: Option[Double]
  )

  private[this] case class TestBeanWithOptionBigDecimal(
    @(Min @field)(value = 10)
    value: Option[BigDecimal]
  )

  Seq(
    (TestBeanWithOptionString(Some("9.9")), 1),
    (TestBeanWithOptionString(Some("10.0")), 0),
    (TestBeanWithOptionInt(Some(9)), 1),
    (TestBeanWithOptionInt(Some(10)), 0),
    (TestBeanWithOptionDouble(Some(9.9)), 1),
    (TestBeanWithOptionDouble(Some(10.0)), 0),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal(9.9))), 1),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal(10.0))), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
