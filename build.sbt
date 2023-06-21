ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.2.2"

lazy val root = (project in file("."))
  .settings(
    name := "PlagueDotScala",
    libraryDependencies += "com.github.sbt" % "junit-interface" % "0.13.3" % Test
)
