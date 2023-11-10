package bitlap.validation

import scala.annotation.meta.field

import org.hibernate.validator.constraints.Length

class LengthValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBean(
    @(Length @field)(min = 1, max = 1)
    value: Option[String]
  )

  Seq(
    (TestBean(Some("")), 1),
    (TestBean(Some("a")), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
