## Support Annotations

Validation of annotation constraints on generic parameters is not supported because types in scala are erased.

## `jakarta.validation.constraints`

> `Option(null)` and `None` are considered valid, while `Some(null)` is considered invalid. Some (null) is a bad habit.
> Only `NotEmpty` and `NotBlank` in this library will be compatible with `Some(null)`, and all other annotations will throw exceptions `IllegalStateException("oops.")`

- AssertFalse
- AssertTrue
- DecimalMax
- DecimalMin
- Digits
- Future
- Max
- Min
- Past
- Pattern
- Size
- Email
- NotBlank
- PastOrPresent
- FutureOrPresent
- NotEmpty
- Negative
- NegativeOrZer
- Positive
- PositiveOrZero
- NotNull

## `jakarta.validation`

- Valid 

## `org.hibernate.validator.constraints`

- CreditCardNumber
- EAN
- Length
- LuhnCheck
- Mod10Check
- Mod11Check
- NotEmpty // deprecated
- Range
- URL

## `bitlap.validation.constraints`

Original annotations.

- AssertNone
- AssertSome
- ByteSize