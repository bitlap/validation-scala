package bitlap.validation

import org.specs2.mutable.Specification

/**
 * Base of all specs.
 */
trait BaseSpec extends Specification {

  def targetClassName =
    this.getClass.getSimpleName.replaceAll("Spec$", "")

  def test(bean: Any, expected: Int) = {
    val validator  = ScalaValidatorFactory.validator
    val violations = validator.validate(bean)
    violations.size must_=== expected
  }

}
