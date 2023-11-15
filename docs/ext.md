# Use it like SpringBoot

> It does not support annotations with group parameters and only supports scala 3!

Add the following code to `build.sbt`:
```scala
libraryDependencies ++= Seq(
"org.bitlap" %% "validation-scala-ext" % "latest version",
),
autoCompilerPlugins := true,
addCompilerPlugin("org.bitlap" %% "validation-scala-plugin" % "latest version")
```

Add `@Validated` to method parameter:
```scala
import bitlap.validation.ext.Validated
def update(
  @Validated person1: Person, 
  @Validated person2: Person
) = {
  /// ...
}
```
Then, checking code will be automatically inserted during compilation and may throw an `IllegalArgumentException` if the constraint checking fails.

If you do not wish to throw an exception directly, you should use `@ValidBinding`.

Just need to add `@ValidBinding` to method parameter, and add a binding parameter to method:
```scala
import bitlap.validation.ext.ValidBinding
def update(
  @ValidBinding persion1: Person,
  @ValidBinding persion2: Person,
  // The plugin captures the binding parameters based on the type, so the name doesn't matter
  bind: BindingResult = BindingResult.default
) = {
  // It will put all violations into the bind parameter.
}
```