assemblyJarName in assembly := "korolev-demo.jar"

val mainClazz = Some("Application")
mainClass in (Compile, run) := mainClazz
mainClass in assembly := mainClazz

lazy val root = project.in(file("."))
  .settings(Settings.settings: _*)
  .settings(Settings.rootSettings: _*)
  .settings(libraryDependencies ++= Dependencies.akkaDependencies)
  .settings(libraryDependencies ++= Dependencies.akkaHttpDependencies)
  .settings(libraryDependencies ++= Dependencies.utilDependencies)
  .settings(libraryDependencies ++= Dependencies.testDependencies)
