package bitlap.validation

class ScalaValidatorFactorySpec extends BaseSpec {

  "ScalaValidatorFactory#validatorFactory" >> {
    "Get a instance of ValidatorFactory" >> {
      val validatorFactory = ScalaValidatorFactory.validatorFactory(new ScalaClockProvider)

      validatorFactory must_!= null
    }

    "Get a instance of Validator" >> {
      val validator = ScalaValidatorFactory.scalaValidator(new ScalaClockProvider)

      validator must_!= null
    }
  }
}
