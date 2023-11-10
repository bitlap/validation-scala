package bitlap.validation

object Utils {

  def checkForOption[T](iterableOnce: IterableOnce[T])(opt: Option[T] => Boolean): Boolean =
    iterableOnce match {
      case option: Option[T] =>
        opt(option)
      case _                 => true
    }

}
