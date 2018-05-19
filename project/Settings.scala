import sbt.Keys.{version, _}
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport.{assemblyMergeStrategy, MergeStrategy}
import sbtassembly.PathList

object Settings {

  val nameV = "korolev-demo"
  val versionV = "0.0.1"
  val scalaV = "2.12.6"

  lazy val settings = Seq(
    name := nameV,
    organization := "com.github.solverit",
    version := versionV,
    scalaVersion := scalaV,
    publishMavenStyle := true,
    publishArtifact in Test := false,
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case PathList("reference.conf")    => MergeStrategy.concat
      case x                             => MergeStrategy.first
    },
    scalacOptions ++= scalaOptions,
    resolvers ++= extResolvers
  )

  val extResolvers = Seq(
    Resolver.mavenLocal
  )

  val scalaOptions = Seq(
    "-deprecation",
    "-encoding",
    "UTF-8", // 2 args
    "-feature",
    "-language:existentials",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-unchecked",
    "-Xfatal-warnings",
    "-Yno-adapted-args",
    "-Ywarn-dead-code", // N.B. doesn't work well with the ??? hole
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Ywarn-unused-import",
    "-Ypartial-unification",
    "-Xfuture"
  )

  lazy val testSettings = Seq(
    fork in Test := false,
    parallelExecution in Test := false,
    test in assembly := {},
    scalacOptions ++= scalaOptions,
    resolvers ++= extResolvers
  )

  lazy val rootSettings = Seq(
    name := nameV,
    version := versionV,
    scalaVersion := scalaV,
    scalacOptions ++= scalaOptions,
    resolvers ++= extResolvers
  )

}
