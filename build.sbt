val dottyVersion = "0.20.0-RC1"
val scala2Version = "2.13.1"
val circeVersion = "0.12.3"

lazy val commonDeps = Seq(
      "org.typelevel" %% "cats-core" % "2.0.0",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion
    )

lazy val scala2root = project
  .in(file("scala2"))
  .settings(
    name := "scala2 module for dotty",
    version := "0.1.0",
     libraryDependencies ++= commonDeps.map(_.withDottyCompat(scalaVersion.value)),
   scalaVersion := scala2Version
  )

lazy val root = project
  .in(file("."))
  .settings(
    name := "dotty-cross",
    version := "0.1.0",

    // Scala 2 compatibility mode
    scalacOptions ++= { if (isDotty.value) Seq("-language:Scala2") else Nil },

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
    libraryDependencies ++= commonDeps.map(_.withDottyCompat(scalaVersion.value)),

    // force project dependences as scala2
    projectDependencies := projectDependencies.value.map(_.withDottyCompat(scalaVersion.value)),

    // To make the default compiler and REPL use Dotty
    scalaVersion := dottyVersion,

    // To cross compile with Dotty and Scala 2
    crossScalaVersions := Seq(dottyVersion, scala2Version)
  ).dependsOn(scala2root)
