package bitlap.validation.extension

import java.lang.reflect.Method

final case class MethodIdentity(
  name: String,
  parameterCount: Int
) {

  def getMethod(obj: Any): Method =
    // TODO
    obj.getClass.getMethods
      .find(m => !m.isBridge && !m.isSynthetic && m.getName == name && m.getParameterCount == parameterCount)
      .orNull
}
