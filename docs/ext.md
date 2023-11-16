# Use it like SpringBoot

> It does not support annotations with group parameters and only supports scala 3!

Add the following code to `build.sbt`:
```scala
libraryDependencies ++= Seq(
"org.bitlap" %% "validation-scala-ext" % "latest version",
)
autoCompilerPlugins := true
addCompilerPlugin("org.bitlap" %% "validation-scala-plugin" % "latest version")
```

## `@Validated`

Add `@Validated` to method parameter:
```scala
import bitlap.validation.ext.Validated

def update(@Validated person1: Person, @Validated person2: Person)
```
Then, checking code will be automatically inserted during compilation and may throw an `IllegalArgumentException` if the constraint checking fails.

If you do not wish to throw an exception directly, you should use `@ValidBinding`.

## `@ValidBinding`

Just need to add `@ValidBinding` to method parameter, and add a `bind: BindingResult` parameter to method:
```scala
import bitlap.validation.ext.ValidBinding

def update(@ValidBinding person1: Person, @ValidBinding person2: Person, bind: BindingResult = BindingResult.default)
```

The plugin captures the `bind` parameters based on the type, so the name doesn't matter.

## Limitation

1. Support only object types
2. The constraints on the parameters are not supported at the moment, for examples:
```scala
def update(@ValidBinding @NotNull person1: Person, bind: BindingResult = BindingResult.default) // the constraint @NotNull will not be used  
def update(@ValidBinding @Positive num: Int, bind: BindingResult = BindingResult.default) // the constraint @Positive will not be used

def update(@Validated @NotNull person1: Person) // the constraint @NotNull will not be used
def update(@Validated @Positive num: Int) // the constraint @Positive will not be used
```

## TODO 

Add scala 3 annotations:
- `@ValidatedParameters`        Support for validating all parameters of a method and throwing `IllegalArgumentException` on demand
- `@ValidBindingParameters`     Support for validating all parameters of a method and catching errors in the `bind: BindingResult` parameter