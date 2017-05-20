package com.pollfish.sbt

import sbt._
import Keys._
import plugins.JvmPlugin
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.ReleaseStateTransformations._
import com.typesafe.sbt.SbtPgp.autoImport.PgpKeys

object Plugin extends AutoPlugin {

  override def requires = JvmPlugin &&
    sbtrelease.ReleasePlugin &&
    com.typesafe.sbt.SbtPgp &&
    scoverage.ScoverageSbtPlugin 
  
  override def trigger = allRequirements

  override val projectSettings: Seq[Setting[_]] = pluginSettings

  lazy val pluginSettings: Seq[Setting[_]] =
    organizationSettings ++ compilationSettings ++ executionSettings ++ publishSettings 

  lazy val executionSettings: Seq[Setting[_]] = Seq(
    fork in Test := true,
    concurrentRestrictions in Global := Seq(
      Tags.limit(Tags.CPU, 2),
      Tags.limit(Tags.Network, 10),
      Tags.limit(Tags.Test, 1),
      Tags.limitAll(15)
    )
  )

  lazy val compilationSettings: Seq[Setting[_]] = Seq(
    scalaVersion := "2.12.2",
    scalacOptions ++= commonScalacOptions,
    scalacOptions in Test ++= (commonScalacOptions ++ Seq("-Yrangepos")),
    updateOptions := updateOptions.value.withCachedResolution(true)
  )

  lazy val organizationSettings: Seq[Setting[_]] = Seq(
    organization := "com.tzavellas",
    organizationName := "spiros.blog()",
    organizationHomepage := Some(url("http://www.tzavellas.com")),
    licenses := Seq("The Apache Software License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    homepage := Some(url("http://www.github.com/sptz45/" + name.value)),
    scmInfo := Some(
      ScmInfo(
        browseUrl = url("http://github.com/sptz45/" + name.value),
        connection = "https://github.com/sptz45/" + name.value +".git",
        devConnection = Some("https://github.com/sptz45/" + name.value +".git")
      )
    ),
    developers := List(
      Developer(
        id = "sptz45",
        name = "Spiros Tzavellas",
        email = "spiros at tzavellas dot com",
        url = url("http://www.tzavellas.com")
      )
    )
  )

  lazy val publishSettings: Seq[Setting[_]] = Seq(
    publishMavenStyle := true,
    pomIncludeRepository := { _ => false },
    publishArtifact in Test := false,
    publishArtifact in (Compile, packageBin) := true,
    publishArtifact in (Compile, packageSrc) := true,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    releasePublishArtifactsAction := PgpKeys.publishSigned.value,
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      publishArtifacts,
      setNextVersion,
      commitNextVersion
    )
  )

  lazy val commonScalacOptions = Seq(
    "-deprecation",
    "-encoding", "UTF-8",
    "-feature",
    "-language:implicitConversions",
    "-unchecked",
    //"-Xfatal-warnings",
    "-Xlint",
    "-Yno-adapted-args",
    "-Ywarn-dead-code",
    //"-Ywarn-numeric-widen",
    //"-Ywarn-value-discard",
    "-Ywarn-infer-any",
    "-Xfuture"
  )
}
