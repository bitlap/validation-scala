package bitlap.validation

import java.util.{ Collections, List => JList, Optional }

import org.hibernate.validator.spi.properties.{ ConstrainableExecutable, GetterPropertySelectionStrategy }

final class ScalaGetterPropertySelectionStrategy extends GetterPropertySelectionStrategy {

  private val methodNamesToIgnore: Set[String] =
    // we will ignore all the method names coming from Object
    classOf[Object].getDeclaredMethods.map(_.getName).toSet ++
      classOf[Product].getDeclaredMethods.map(_.getName).toSet

  def getProperty(executable: ConstrainableExecutable): Optional[String] =
    if (
      executable.getName.contains("$") ||
      methodNamesToIgnore.contains(executable.getName) || executable.getReturnType.equals(
        classOf[Unit]
      ) || executable.getParameterTypes.length > 0
    ) Optional.empty
    else Optional.of(executable.getName)

  def getGetterMethodNameCandidates(propertyName: String): JList[String] =
    // As method name == property name, there always is just one possible name for a method
    Collections.singletonList(propertyName)
}
