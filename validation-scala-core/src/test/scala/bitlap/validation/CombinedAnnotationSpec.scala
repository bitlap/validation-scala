package bitlap.validation

import scala.annotation.meta.field
import scala.annotation.nowarn

import org.hibernate.validator.constraints.{ CreditCardNumber, Length, NotEmpty, Range => RangeAnnotation }

import jakarta.validation.Valid

@nowarn
class CombinedAnnotationSpec extends BaseSpec {

  private[this] case class TestBeanForCreditCardNumber(
    @(CreditCardNumber @field)
    value: Option[String]
  )

  Seq(
    (TestBeanForCreditCardNumber(Some("00000001")), 1),
    (TestBeanForCreditCardNumber(Some("00000000")), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }

  private[this] case class TestBeanWithOptionStringForNotEmpty(
    @(NotEmpty @field)
    value: Option[String]
  )

  private[this] case class TestBeanWithOptionArrayForNotEmpty(
    @(NotEmpty @field)
    value: Option[Array[String]]
  )

  Seq(
    (TestBeanWithOptionStringForNotEmpty(Some("")), 1),
    (TestBeanWithOptionStringForNotEmpty(Some(" ")), 0)
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

  private[this] case class TestBeanForRange(
    @(RangeAnnotation @field)(min = 1, max = 1)
    value: Option[Int]
  )

  Seq(
    (TestBeanForRange(Some(2)), 1),
    (TestBeanForRange(Some(1)), 0)
  ) foreach { case (bean, expected) =>
    s"Check violations count. bean = $bean, count = $expected" >> {
      test(bean, expected)
    }
  }

  // not support cascad
//  case class MyBean(
//    @(Valid @field)
//    list: Seq[InnerBean]
//  )
//
//  case class InnerBean(
//    @(Length @field)(max = 2)
//    name: String
//  )
//
//  Seq(
//    (MyBean(Seq(InnerBean("1"))), 0),
//    (MyBean(Seq(InnerBean("123"))), 1)
//  ) foreach { case (bean, expected) =>
//    s"Check violations count. bean = $bean, count = $expected" >> {
//      test(bean, expected)
//    }
//  }
}
