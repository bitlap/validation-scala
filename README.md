# validation-scala

JSR 303 and 349 Bean Validation for Scala.
This library enable validation to some Monad. (e.g. Option, Seq, Map, etc)

[![Project stage][Badge-Stage]][Badge-Stage-Page] ![CI][Badge-CI] [![Nexus (Snapshots)][Badge-Snapshots]][Link-Snapshots] [![Sonatype Nexus (Releases)][Badge-Releases]][Link-Releases]

[Badge-Stage]: https://img.shields.io/badge/Project%20Stage-Experimental-orange.svg
[Badge-Stage-Page]: https://github.com/bitlap/bitlap/wiki/Project-Stages

[Badge-CI]: https://github.com/bitlap/validation-scala/actions/workflows/scala.yml/badge.svg
[Badge-Snapshots]: https://img.shields.io/nexus/s/org.bitlap/validation-scala_3?server=https%3A%2F%2Fs01.oss.sonatype.org
[Link-Snapshots]: https://oss.sonatype.org/content/repositories/snapshots/org/bitlap/validation-scala_3/

[Badge-Releases]: https://img.shields.io/nexus/r/org.bitlap/validation-scala_3?server=https%3A%2F%2Fs01.oss.sonatype.org
[Link-Releases]: https://oss.sonatype.org/content/repositories/releases/org/bitlap/validation-scala_3/

## Getting Started

Support Scala `3`, `2.13`:
```scala
libraryDependencies ++= Seq(
  "org.bitlap" %% "validation-scala" % "latest version",
  
  // zio wrapper
  // "org.bitlap" %% "validation-scala-fp" % "latest version",
  
  // NOTE: for use above Java 9, the following dependencies are required.
  // "jakarta.xml.bind"        % "jakarta.xml.bind-api"    % "2.3.2",
  // "org.glassfish.jaxb"      % "jaxb-runtime"            % "2.3.2"
)
```

## How to use

Step 1. Add annotations to your case class:
```scala
import javax.validation.constraints.Size

case class Person(
  @(Size @field)(min = 4)
  name: Option[String]
)
```

Step 2. Create validator by our library:
```scala
import bitlap.validation.ScalaValidatorFactory

val validator = ScalaValidatorFactory.scalaValidator
```

Step 3. Validate a case class object:
```scala
val obj = Person(Some("abc"))
val violations = validator.validate(obj)

if (violations.nonEmpty) {
  println("Violations found!")
}

```

## Other information

### Support annotations

`javax.validation.constraints`

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

`org.hibernate.validator.constraints`

- CreditCardNumber
- EAN
- Email
- Length
- LuhnCheck
- Mod10Check
- Mod11Check
- NotBlank
- NotEmpty
- Range
- URL

`bitlap.validation`

Original annotations.

- AssertNone
- AssertSome
- ByteSize

### Doesn't support annotations

- NotNull `// What do you think I should do?`
- Null
- ModCheck `// Deprecated`
- ParameterScriptAssert
- ScriptAssert


## Contribution

I'm seeking your PR!!!
I'm easy.

For example.

- Bug fix
- Refactoring the code.
- Add a new feature, new annotations and others.
- Fix my odd English texts.

## License

```
Copyright 2015 - 2017 tsukaby.com
MIT License
```
