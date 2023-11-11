package bitlap.validation

import scala.annotation.meta.field

import jakarta.validation.constraints.Size

class SizeValidatorForOptionSpec extends BaseSpec {

  private[this] case class TestBeanWithOptionString(
    @(Size @field)(min = 1)
    name: Option[String]
  )

  private[this] case class TestBeanWithOptionArray(
    @(Size @field)(min = 1)
    name: Option[Array[String]]
  )

  private[this] case class TestBeanWithOptionSeq(
    @(Size @field)(min = 1)
    name: Option[Seq[String]]
  )

  private[this] case class TestBeanWithOptionSet(
    @(Size @field)(min = 1)
    name: Option[Set[String]]
  )

  private[this] case class TestBeanWithOptionMap(
    @(Size @field)(min = 1)
    name: Option[Map[String, String]]
  )

  Seq(
    (TestBeanWithOptionString(Some("")), 1),
    (TestBeanWithOptionString(Some("1")), 0),
    (TestBeanWithOptionArray(Some(Array())), 1),
    (TestBeanWithOptionArray(Some(Array("1"))), 0),
    (TestBeanWithOptionSeq(Some(Seq())), 1),
    (TestBeanWithOptionSeq(Some(Seq("1"))), 0),
    (TestBeanWithOptionSet(Some(Set())), 1),
    (TestBeanWithOptionSet(Some(Set("1"))), 0),
    (TestBeanWithOptionMap(Some(Map())), 1),
    (TestBeanWithOptionMap(Some(Map("1" -> "1"))), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }
}
