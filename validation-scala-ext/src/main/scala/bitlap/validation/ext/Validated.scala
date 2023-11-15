package bitlap.validation.ext

import scala.annotation.{ meta, StaticAnnotation }

@meta.param
final case class Validated() extends StaticAnnotation
