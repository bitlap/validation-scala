package bitlap.validation.ext

import bitlap.validation.Identity

object Preconditions {

  def validateArgs(objs: Any*): Identity[Boolean] =
    objs.forall(o => Validator.checkArgs(o))

}
