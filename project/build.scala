import sbt._
import Keys._

object build extends Build {
    val sharedSettings = Defaults.defaultSettings ++ Seq(
        organization := "test",
        version := "0.1",
        scalaVersion := "2.10.0-M7",
        scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),

        libraryDependencies <<= scalaVersion { scala_version =>
            Seq(
                "com.typesafe.akka" %% "akka-actor" % "2.1-SNAPSHOT" cross CrossVersion.full
            )
        })

    lazy val root = Project(
        id = "test",
        base = file("."),
        settings = sharedSettings
    )
}
