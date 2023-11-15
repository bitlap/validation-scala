package bitlap.validation.plugin

import dotty.tools.dotc.config.*
import dotty.tools.dotc.plugins.*
import dotty.tools.dotc.transform.*

class ValidationCompilerPlugin extends StandardPlugin:
  self =>

  override val name: String        = ValidationCompilerPlugin.name
  override val description: String = ValidationCompilerPlugin.description

  def init(options: List[String]): List[PluginPhase] =
    new ValidationArgsPhase() :: Nil

end ValidationCompilerPlugin

object ValidationCompilerPlugin:
  val name        = "ValidationCompilerPlugin"
  val description = "Validation Compiler Plugin"
end ValidationCompilerPlugin
