# validation-scala

Jakarta Bean Validation 3.0 for Scala. Allows validation of some Scala types: `Option`, `Seq`, `Set`, `Map`.

Supports Scala `3` and `2.13`, above Java `11`.

[![Project stage][Badge-Stage]][Badge-Stage-Page] ![CI][Badge-CI] [![Nexus (Snapshots)][Badge-Snapshots]][Link-Snapshots] [![Sonatype Nexus (Releases)][Badge-Releases]][Link-Releases]

[Badge-Stage]: https://img.shields.io/badge/Project%20Stage-Experimental-orange.svg
[Badge-Stage-Page]: https://github.com/bitlap/bitlap/wiki/Project-Stages

[Badge-CI]: https://github.com/bitlap/validation-scala/actions/workflows/scala.yml/badge.svg
[Badge-Snapshots]: https://img.shields.io/nexus/s/org.bitlap/validation-scala-core_3?server=https%3A%2F%2Fs01.oss.sonatype.org
[Link-Snapshots]: https://s01.oss.sonatype.org/content/repositories/snapshots/org/bitlap/validation-scala-core_3/

[Badge-Releases]: https://img.shields.io/nexus/r/org.bitlap/validation-scala-core_3?server=https%3A%2F%2Fs01.oss.sonatype.org
[Link-Releases]: https://s01.oss.sonatype.org/content/repositories/releases/org/bitlap/validation-scala-core_3/

## Introduction

```scala
libraryDependencies ++= Seq(
  "org.bitlap" %% "validation-scala-core" % "latest version"
)
```

## Usage Instructions

Step 1. Add annotations to your case class:
```scala
import jakarta.validation.constraints.Size

case class Person(
  @(Size @field)(min = 4)
  name: Option[String]
)
```

Step 2. Create validator by our library:
```scala
import bitlap.validation.ScalaValidatorFactory

val validator = ScalaValidatorFactory.scalaValidator()
```

Step 3. Validate a case class object:
```scala
val obj = Person(Some("abc"))
val violations = validator.validate(obj)

if (violations.nonEmpty) {
  println("Violations found!")
}
```

### Scala 3 Compiler Plugin

> It only supports scala 3!

Add the following code to `build.sbt`:
```scala
libraryDependencies ++= Seq(
"org.bitlap" %% "validation-scala-ext" % "latest version",
),
autoCompilerPlugins := true,
addCompilerPlugin("org.bitlap" %% "validation-scala-plugin" % "latest version")
```

Add `@checkArgument` on method paramemter:
```scala
import bitlap.validation.ext.checkArgument
def update(@checkArgument persion1: Persion, persion2: Persion) = {
  /// ...
}
```

The following code is the expanded code of the compiler plugin:
```scala
def update(@checkArgument persion1: Persion, @checkArgument persion2: Persion) = {
  bitlap.validation.ext.ValidationRuntimeUtil.checkArgument(persion1, persion2)
  /// ...
}
```

## Other information

### Support annotations

`jakarta.validation.constraints`

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

`org.hibernate.validator.constraints`

- CreditCardNumber
- EAN
- Length
- LuhnCheck
- Mod10Check
- Mod11Check
- NotEmpty `deprecated`
- Range
- URL

`bitlap.validation.constraints`

Original annotations.

- AssertNone
- AssertSome
- ByteSize

## Inspired by

[bean-validation-scala](https://github.com/bean-validation-scala/bean-validation-scala), JSR 303 and 349 Bean Validation for Scala.