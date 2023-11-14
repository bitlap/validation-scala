package bitlap.validation

import java.time.{ Clock, ZoneId }

class ScalaValidatorFactorySpec extends BaseSpec {

  "ScalaValidatorFactory#validatorFactory" >> {
    "Get a instance of ValidatorFactory" >> {
      val validatorFactory = ScalaValidatorFactory.validatorFactory()

      validatorFactory must_!= null
    }

    "Get a instance of Validator" >> {
      val validator = ScalaValidatorFactory.scalaValidator()

      validator must_!= null
    }
  }
}
