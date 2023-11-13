package bitlap.validation

import scala.annotation.meta.field
import jakarta.validation.constraints.Positive

class PositiveValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionInt(
    @(Positive @field)
    name: Option[Int]
  )

  private[this] case class TestBeanWithOptionInteger(
    @(Positive @field)
    name: Option[Integer]
  )

  private[this] case class TestBeanWithOptionBigDecimal(
    @(Positive @field)
    name: Option[BigDecimal]
  )

  Seq(
    (TestBeanWithOptionInt(Some(0)), 1),
    (TestBeanWithOptionInt(Some(2)), 0),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(0))), 1),
    (TestBeanWithOptionInteger(Some(Integer.valueOf(2))), 0),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("0"))), 1),
    (TestBeanWithOptionBigDecimal(Some(BigDecimal("1"))), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
