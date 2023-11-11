package bitlap.validation

import scala.annotation.meta.field

class AssertSomeValidatorSpec extends BaseSpec {

  private[this] case class TestBean(
    @(AssertSome @field)
    value: Option[Int]
  )

  private[this] case class TestBeanWithString(
    @(AssertSome @field)
    value: Option[String]
  )

  Seq(
    (TestBean(None), 1),
    (TestBean(Some(1)), 0),
    (TestBeanWithString(None), 1),
    (TestBeanWithString(Some("")), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }

}
