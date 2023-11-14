package bitlap.validation.function

import org.hibernate.validator.internal.constraintvalidators.bv.AssertFalseValidator

import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.constraints.AssertFalse

trait CheckOptionFunction extends CheckFunction[Option[_], Boolean]
