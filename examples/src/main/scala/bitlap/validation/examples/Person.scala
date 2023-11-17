package bitlap.validation.examples

import jakarta.validation.constraints.Size
import scala.annotation.meta.field

final case class Person(
  // @(Size @getter)(min = 4) also supports the getter method
  @(Size @field)(min = 4)
  name: Option[String]
)
