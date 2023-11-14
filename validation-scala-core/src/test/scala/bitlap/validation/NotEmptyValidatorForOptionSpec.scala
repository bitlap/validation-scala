package bitlap.validation

import scala.annotation.meta.field

import jakarta.validation.constraints.NotEmpty

class NotEmptyValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionStringForNotEmpty(
    @(NotEmpty @field)
    value: Option[String]
  )

  private[this] case class TestBeanWithOptionArrayForNotEmpty(
    @(NotEmpty @field)
    value: Option[Array[String]]
  )

  Seq(
    (TestBeanWithOptionStringForNotEmpty(Some(null)), 1),
    (TestBeanWithOptionStringForNotEmpty(Some("")), 1),
    (TestBeanWithOptionStringForNotEmpty(Some(" ")), 0),
    (TestBeanWithOptionStringForNotEmpty(None), 0) // should be true?
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }

  Seq(
    (TestBeanWithOptionArrayForNotEmpty(Some(Array())), 1),
    (TestBeanWithOptionArrayForNotEmpty(Some(Array(""))), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
