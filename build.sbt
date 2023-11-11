import sbt.Keys.crossScalaVersions

val scala3_Version               = "3.3.1"
val scala2_13Version             = "2.13.12"
val scalaCollectionCompatVersion = "2.11.0"
val hibernateVersion             = "5.4.0.Final"
val validationVersion            = "1.1.0.Final"
val elVersion                    = "3.0.0"
val jodaConvertVersion           = "2.2.2"
val jodaTimeVersion              = "2.12.5"
val specs2Version                = "4.19.2"
val zioVersion                   = "2.0.13"

val supportCrossVersionList = Seq(scala3_Version, scala2_13Version)

inThisBuild(
  List(
    scalaVersion           := scala2_13Version,
    organization           := "org.bitlap",
    sonatypeCredentialHost := "s01.oss.sonatype.org",
    sonatypeRepository     := "https://s01.oss.sonatype.org/service/local",
    homepage               := Some(url("https://github.com/bitlap/rolls")),
    licenses               := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
    developers             := List(
      Developer(
        id = "jxnu-liguobin",
        name = "梦境迷离",
        email = "dreamylost@outlook.com",
        url = url("https://blog.dreamylost.cn")
      )
    )
  )
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

lazy val commonSettings = Seq(
  crossScalaVersions := supportCrossVersionList,
  // Add warnings
  scalacOptions ++= Seq(
    "-feature",
    "-unchecked",
    "-deprecation"
  )
)

lazy val root = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name           := "validation-scala",
    publish / skip := true
  )
  .aggregate(zio, core)

lazy val zio = project
  .in(file("zio"))
  .settings(commonSettings)
  .settings(
    name := "validation-scala-zio",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion % Provided
    )
  )
  .dependsOn(core)

lazy val core = project
  .in(file("core"))
  .settings(commonSettings)
  .settings(
    name := "validation-scala",
    Compile / doc / scalacOptions ++= {
      // Work around 2.12 bug which prevents javadoc in nested java classes from compiling.
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, v)) if v == 12 =>
          Seq("-no-java-comments")
        case _                       =>
          Nil
      }
    },
    libraryDependencies ++= Seq(
      "org.hibernate"           % "hibernate-validator"     % hibernateVersion,
      "javax.validation"        % "validation-api"          % validationVersion,
      "org.glassfish"           % "javax.el"                % elVersion,
      "org.joda"                % "joda-convert"            % jodaConvertVersion,
      "joda-time"               % "joda-time"               % jodaTimeVersion,
//      "jakarta.xml.bind"        % "jakarta.xml.bind-api"    % "2.3.2",
//      "org.glassfish.jaxb"      % "jaxb-runtime"            % "2.3.2",
      "org.scala-lang.modules" %% "scala-collection-compat" % scalaCollectionCompatVersion,
      "org.specs2"             %% "specs2-core"             % specs2Version % Test
    )
  )
