scalaVersion := "2.13.2"
name := "Earthdawn Generators"
version := "0.1.0-SNAPSHOT"
scalacOptions ++= Seq("-deprecation")

libraryDependencies ++= Seq(
  "com.lihaoyi"       %% "fastparse"       % "2.2.3",
  "org.typelevel"     %% "cats-core"       % "2.0.0",
  "io.circe"          %% "circe-yaml"      % "0.12.0",
  "org.scalatest"     %% "scalatest"       % "3.1.2"   % Test,
  "org.scalatestplus" %% "scalacheck-1-14" % "3.1.0.0" % Test
)
