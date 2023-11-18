# Use it like SpringBoot

> It does not support annotations with group parameters and only supports scala 3!

Add the following code to `build.sbt`:
```scala
libraryDependencies ++= Seq(
  "org.bitlap" %% "validation-scala-core" % "latest version",
)
autoCompilerPlugins := true
addCompilerPlugin("org.bitlap" %% "validation-scala-plugin" % "latest version")
```

## `@Validated`

Add `@Validated` to method:
```scala
import bitlap.validation.extension.Validated

@Validated def update(person1: Person, person2: Person)
```
Then, checking code will be automatically inserted during compilation and may throw an `IllegalArgumentException` if the constraint checking fails.

If you do not wish to throw an exception directly, you should use `@ValidBinding`.

## `@ValidBinding`

Just need to add `@ValidBinding` to method, and add a `bind: BindingResult` parameter to method:
```scala
import bitlap.validation.extension.ValidBinding

@ValidBinding def update(person1: Person, person2: Person, bind: BindingResult = BindingResult.default)
```

The plugin captures the `bind` parameters based on the type, so the name doesn't matter.

## Cascade Validation

```scala
@Validated def validatedTwoParams(@Valid person1: Person, @Valid person2: Person): String

@Validated def validatedOneParams(@Valid person1: Person): String

@ValidBinding def validatedBindParams(@Valid person1: Person, bindingError: BindingResult = BindingResult.default): String

@Validated def validatedNotNullParams(@Valid @NotNull person1: Person): String
```

## Non-Cascade Validation

```scala
@Validated def validatedNotNullParams(@NotNull person1: Person): String

@Validated def validatedNotEmptyParam(@NotBlank name: String): String
```

## Limitation

- Only instance methods of classes are supported due to the limitations of jakarta
- Overloading methods with the same number of parameters are not supported.