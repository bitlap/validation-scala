package bitlap.validation

import scala.annotation.meta.field

import org.hibernate.validator.constraints.URL

class URLValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBean(
    @(URL @field)
    value: Option[String]
  )

  Seq(
    (TestBean(Some("htttp://example.com")), 1),
    (TestBean(Some("http://example.com")), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
