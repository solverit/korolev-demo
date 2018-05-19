import sbt._

object Dependencies {

  lazy val version = new {
    val korolev   = "0.8.1"
    val akka      = "2.5.8"
    val akkaHttp  = "10.0.11"
    val upickle   = "0.5.1"
    val scalaTest = "3.0.5"
    val logback   = "1.2.3"
  }

  lazy val library = new {
    val korolev = "com.github.fomkin" %% "korolev-server-akkahttp" % version.korolev

    val akkaActor  = "com.typesafe.akka" %% "akka-actor"  % version.akka
    val akkaStream = "com.typesafe.akka" %% "akka-stream" % version.akka
    val akkaSlf4j  = "com.typesafe.akka" %% "akka-slf4j"  % version.akka

    val akkaHttp = "com.typesafe.akka" %% "akka-http" % version.akkaHttp
    val upickle  = "com.lihaoyi"       %% "upickle"   % version.upickle

    val akkaTestKit       = "com.typesafe.akka" %% "akka-testkit"        % version.akka      % Test
    val akkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % version.akka      % Test
    val akkaHttpTestKit   = "com.typesafe.akka" %% "akka-http-testkit"   % version.akkaHttp  % Test
    val scalaTest         = "org.scalatest"     %% "scalatest"           % version.scalaTest % Test

    val logbackClassic = "ch.qos.logback" % "logback-classic" % version.logback
  }

  val testDependencies: Seq[ModuleID] = Seq(
    library.akkaTestKit,
    library.akkaStreamTestKit,
    library.akkaHttpTestKit,
    library.scalaTest
  )

  val akkaDependencies: Seq[ModuleID] = Seq(
    library.korolev,
    library.akkaActor,
    library.akkaStream,
    library.akkaSlf4j
  )

  val akkaHttpDependencies: Seq[ModuleID] = Seq(
    library.akkaHttp,
    library.upickle
  )

  val utilDependencies: Seq[ModuleID] = Seq(
    library.logbackClassic
  )

}
