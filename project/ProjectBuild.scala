import sbt._
import Keys._

object ProjectBuild {

  object Dependencies {
    val scalatest = "org.scalatest" %% "scalatest" % "1.8"
    val dispatch = "net.databinder.dispatch" %% "dispatch-core" % "0.9.3"
  }

  val params = Seq(
    organization := "com.mansoor",
    version := "0.01-SNAPSHOT",
    scalaVersion := "2.9.2",
    javacOptions ++= Seq("-target", "1.6", "-source", "1.6"))

  lazy val projectSettings = Project.defaultSettings ++= params

  lazy val baseProject = Project(
    id = "strangeloop2012-analytics",
    base = file("."),
    settings = projectSettings
  )


}