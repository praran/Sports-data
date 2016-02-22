
name := "Sports-data"
version := "1.0"
scalaVersion := "2.11.6"
crossPaths := false


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.0" % "test",
  "org.specs2" %% "specs2-core" % "2.4.11" % "test",
  "org.mockito" % "mockito-core" % "1.9.5" % "test"
)


mainClass in (run) := Some("com.img.Application")

