package bitlap.validation.ext

import scala.util.control.NonFatal

import bitlap.validation.Identity

object ValidationRuntimeUtil {

  def checkArgument(objs: Any*): Identity[Boolean] =
    objs.forall(o => Validator.checkArgument(o))

}
