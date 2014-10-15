import AssemblyKeys._ // put this at the top of the file

name := "9DeadMenMorris"

version := "1.0"

scalacOptions ++= Seq("-optimise", "-deprecation", "-unchecked", "-Yinline-warnings", "-feature")

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "2.0.2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.173"
)

assemblySettings

mainClass in assembly := Some("Client")

mainClass in (Compile, run) := Some("Client")

mainClass in (Compile, packageBin) := Some("Client")