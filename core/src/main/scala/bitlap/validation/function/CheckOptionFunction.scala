package bitlap.validation.function

import javax.validation.ConstraintValidatorContext
import javax.validation.constraints.AssertFalse

import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator

trait CheckOptionFunction extends CheckFunction[Option[_], Boolean]
