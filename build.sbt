import sbt.Keys.crossScalaVersions

ThisBuild / resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at "https://s01.oss.sonatype.org/content/repositories/releases"
)

val scala3_Version               = "3.3.4"
val scala2_13Version             = "2.13.15"
val scalaCollectionCompatVersion = "2.12.0"
val hibernateVersion             = "8.0.1.Final"
val jakartaVersion               = "3.1.0"
val elVersion                    = "5.0.0"
val jodaConvertVersion           = "2.2.4"
val jodaTimeVersion              = "2.13.0"
val specs2Version                = "4.20.9"
val zioVersion                   = "2.1.11"
val `example-dependency-version` = "1.0.0-RC1"
val supportCrossVersionList      = Seq(scala3_Version, scala2_13Version)

inThisBuild(
  List(
    scalaVersion           := scala3_Version,
    organization           := "org.bitlap",
    sonatypeCredentialHost := "s01.oss.sonatype.org",
    sonatypeRepository     := "https://s01.oss.sonatype.org/service/local",
    homepage               := Some(url("https://github.com/bitlap/validation-scala")),
    licenses               := List("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0")),
    developers             := List(
      Developer(
        id = "jxnu-liguobin",
        name = "梦境迷离",
        email = "dreamylost@outlook.com",
        url = url("https://github.com/jxnu-liguobin")
      )
    )
  )
)

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")

lazy val commonSettings = Seq(
  // Add warnings
  scalacOptions ++= Seq(
    "-feature",
    "-unchecked",
    "-deprecation"
  )
)

lazy val `validation-scala` = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name           := "validation-scala",
    publish / skip := true
  )
  .aggregate(
    `validation-scala-ext`,
    `validation-scala-core`,
    `validation-scala-plugin`,
    `validation-scala-extractor`
  )

lazy val `examples` = (project in file("examples"))
  .settings(
    commonSettings,
    publish / skip      := true,
    scalaVersion        := scala3_Version,
    crossScalaVersions  := Nil,
    name                := "examples",
    autoCompilerPlugins := true,
    scalacOptions ++= Seq(
      "-Ydebug"
    ),
    addCompilerPlugin("org.bitlap" %% "validation-scala-plugin" % `example-dependency-version`),
    libraryDependencies ++= Seq(
      "org.bitlap" %% "validation-scala-core" % `example-dependency-version`,
      "org.bitlap" %% "validation-scala-ext"  % `example-dependency-version`,
      "dev.zio"    %% "zio"                   % zioVersion
    )
  )

lazy val `validation-scala-ext` = project
  .in(file("validation-scala-ext"))
  .settings(commonSettings)
  .settings(
    crossScalaVersions               := supportCrossVersionList,
    name                             := "validation-scala-ext",
    scalaVersion                     := scala3_Version,
    libraryDependencies += "dev.zio" %% "zio" % zioVersion % Provided
  )
  .dependsOn(`validation-scala-core` % "compile->compile;test->test")

lazy val `validation-scala-plugin` = (project in file("validation-scala-plugin"))
  .settings(
    commonSettings,
    name               := "validation-scala-plugin",
    scalaVersion       := scala3_Version,
    crossScalaVersions := Nil,
    libraryDependencies ++= List(
      "org.scala-lang" %% "scala3-compiler" % scala3_Version % Provided
    )
  )

lazy val `validation-scala-extractor` = project
  .in(file("validation-scala-extractor"))
  .settings(commonSettings)
  .settings(
    name               := "validation-scala-extractor",
    crossScalaVersions := Nil,
    scalaVersion       := scala3_Version,
    libraryDependencies ++= Seq(
      "jakarta.validation" % "jakarta.validation-api" % jakartaVersion
    )
  )

lazy val `validation-scala-core` = project
  .in(file("validation-scala-core"))
  .settings(commonSettings)
  .settings(
    name               := "validation-scala-core",
    crossScalaVersions := supportCrossVersionList,
    scalaVersion       := scala3_Version,
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
      "org.hibernate.validator" % "hibernate-validator"     % hibernateVersion,
      "org.joda"                % "joda-convert"            % jodaConvertVersion,
      "joda-time"               % "joda-time"               % jodaTimeVersion,
      "org.glassfish.expressly" % "expressly"               % elVersion,
      "org.scala-lang.modules" %% "scala-collection-compat" % scalaCollectionCompatVersion,
      "org.specs2"             %% "specs2-core"             % specs2Version % Test
    )
  )
  .dependsOn(`validation-scala-extractor`)
