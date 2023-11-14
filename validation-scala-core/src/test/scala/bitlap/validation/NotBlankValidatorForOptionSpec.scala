package bitlap.validation

import scala.annotation.meta.field

import jakarta.validation.constraints.NotBlank

class NotBlankValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBean(
    @(NotBlank @field)
    value: Option[String]
  )

  Seq(
    (TestBean(Some(null)), 1),
    (TestBean(Some("")), 1),
    (TestBean(Some(" ")), 1),
    (TestBean(Some("a")), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
