# validation-scala

Jakarta Bean Validation 3.0 for Scala. Allows validation of some Scala types (e.g. `Option`, `Seq`, `Set`, `Map`, etc).

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
  // @(Size @getter)(min = 4) also supports the getter method
  @(Size @field)(min = 4)
  name: Option[String]
)
```

Step 2. Create validator by our library:
```scala
import bitlap.validation.ScalaValidatorFactory
import bitlap.validation.ScalaClockProvider

val validator = ScalaValidatorFactory.scalaValidator(new ScalaClockProvider)
```

Step 3. Validate a case class object:
```scala
val obj = Person(Some("abc"))
val violations = validator.validate(obj)

if (violations.nonEmpty) {
  println("Violations found!")
}
```

## Use it like SpringBoot

> It does not support annotations with group parameters and only supports scala 3!

Add the following code to `build.sbt`:
```scala
libraryDependencies ++= Seq(
  "org.bitlap" %% "validation-scala-core" % "latest version",
  // "org.bitlap" %% "validation-scala-ext" % "latest version", // for zio
)
autoCompilerPlugins := true
addCompilerPlugin("org.bitlap" %% "validation-scala-plugin" % "latest version")
```

### `@Validated`

Add `@Validated` to method:
```scala
import bitlap.validation.extension.Validated

@Validated def update(person1: Person, person2: Person)
```
Then, checking code will be automatically inserted during compilation and may throw an `IllegalArgumentException` if the constraint checking fails.

If you do not wish to throw an exception directly, you should use `@ValidBinding`.

### `@ValidBinding`

Just need to add `@ValidBinding` to method, and add a `bind: BindingResult` parameter to method:
```scala
import bitlap.validation.extension.ValidBinding

@ValidBinding def update(person1: Person, person2: Person, bind: BindingResult = BindingResult.default)
```

The plugin captures the `bind` parameters based on the type, so the name doesn't matter.

### Cascade Validation

```scala
import jakarta.validation.Valid
import bitlap.validation.extension.Validated
import bitlap.validation.extension.ValidBinding
import jakarta.validation.constraints.NotNull

@Validated def validatedTwoParams(@Valid person1: Person, @Valid person2: Person): String

@Validated def validatedOneParams(@Valid person1: Person): String

@ValidBinding def validatedBindParams(@Valid person1: Person, bindingError: BindingResult = BindingResult.default): String

@Validated def validatedNotNullParams(@Valid @NotNull person1: Person): String
```

### Non-Cascade Validation

```scala
import bitlap.validation.extension.Validated
import jakarta.validation.constraints.{ NotBlank, NotNull }

@Validated def validatedNotNullParams(@NotNull person1: Person): String

@Validated def validatedNotEmptyParam(@NotBlank name: String): String
```

### Limitation

- Only instance methods of classes are supported due to the limitations of jakarta
- Overloading methods with the same number of parameters are not supported.

## Other information

1. [Supported Annotations](./docs/support-annotations.md)
2. [Normal Examples](./examples/src/main/scala/bitlap/validation/examples/PersonNormalService.scala)
3. [ZIO Examples](./examples/src/main/scala/bitlap/validation/examples/PersonZioService.scala)

## Inspired by

[bean-validation-scala](https://github.com/bean-validation-scala/bean-validation-scala), JSR 303 and 349 Bean Validation for Scala.