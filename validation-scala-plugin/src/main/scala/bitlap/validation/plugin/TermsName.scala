package bitlap.validation.plugin

private[plugin] object TermsName {

  final val BindingResult_Class    = "bitlap.validation.extension.BindingResult"
  final val Preconditions_Class    = "bitlap.validation.extension.Preconditions"
  final val ZioPreconditions_Class = "bitlap.validation.ext.ZioPreconditions"
  final val Zio_Class              = "zio.ZIO"

  final val ValidateMethodArgs_Method        = "validateMethodArgs"
  final val ValidateMethodArgsBinding_Method = "validateMethodArgsBinding"
  final val MethodIdentity_Class             = "bitlap.validation.extension.MethodIdentity"

  object SupportAnnotations {

    val values: Seq[String] = Seq(
      "AssertFalse",
      "AssertTrue",
      "DecimalMax",
      "DecimalMin",
      "Digits",
      "Future",
      "Max",
      "Min",
      "Past",
      "Pattern",
      "Size",
      "Email",
      "NotBlank",
      "PastOrPresent",
      "FutureOrPresent",
      "NotEmpty",
      "Negative",
      "NegativeOrZer",
      "Positive",
      "PositiveOrZero",
      "CreditCardNumber",
      "EAN",
      "Length",
      "LuhnCheck",
      "Mod10Check",
      "Mod11Check",
      "NotEmpty",
      "Range",
      "URL",
      "NotNull"
    ).map(a => s"jakarta.validation.constraints.$a") ++
      Seq("AssertNone", "AssertSome", "ByteSize").map(a => s"bitlap.validation.$a") ++
      Seq("jakarta.validation.Valid")
  }
}
