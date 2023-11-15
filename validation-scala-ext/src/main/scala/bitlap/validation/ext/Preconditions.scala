package bitlap.validation.ext

import bitlap.validation.Identity

object Preconditions {

  def validateArgs(objs: Any*): Identity[Unit] =
    objs.foreach(o => Validator.checkArgs(o))

}
