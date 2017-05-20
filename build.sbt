organization := "com.tzavellas"
name := "sse-sbt-plugin"
version := "0.1"

description := "An sbt plugin with common settings for all sse projects"

sbtPlugin := true

publishMavenStyle := false


addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC3")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.0")
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
addSbtPlugin("com.typesafe.sbt" % "sbt-license-report" % "1.0.0")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.5")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")


organizationName := "spiros.blog()"
organizationHomepage := Some(url("http://www.tzavellas.com"))
licenses := Seq("The Apache Software License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("http://www.github.com/sptz45/" + name.value))
scmInfo := Some(
  ScmInfo(
    browseUrl = url("http://github.com/sptz45/" + name.value),
    connection = "https://github.com/sptz45/" + name.value +".git",
    devConnection = Some("https://github.com/sptz45/" + name.value +".git")
  )
)
developers := List(
  Developer(
    id = "sptz45",
    name = "Spiros Tzavellas",
    email = "spiros at tzavellas dot com",
    url = url("http://www.tzavellas.com")
  )
)
