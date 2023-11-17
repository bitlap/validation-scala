package bitlap.validation.extension

import scala.annotation.{ meta, StaticAnnotation }

@meta.param
final case class ValidBinding() extends StaticAnnotation
