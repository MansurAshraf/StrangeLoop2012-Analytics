import sbt._
import Keys._


object ProjectBuild extends Build {

  import ProjectBuild.Dependencies._

  object Dependencies {
    val scalatest = "org.scalatest" %% "scalatest" % "1.8"
    val tikaCore = "org.apache.tika" % "tika-core" % "1.2"
    val tikaParser = "org.apache.tika" % "tika-parsers" % "1.2"
    val luceneCore= "org.apache.lucene" % "lucene-core" % "3.6.1"
    val luceneAnalyzer="org.apache.lucene" % "lucene-analyzers" % "3.6.1"
    val jgit="org.eclipse.jgit" % "org.eclipse.jgit" % "2.0.0.201206130900-r"
  }

  val params = Seq(
    organization := "com.mansoor",
    version := "0.01-SNAPSHOT",
    scalaVersion := "2.9.2",
    javacOptions ++= Seq("-target", "1.6", "-source", "1.6"))

  lazy val projectSettings = Project.defaultSettings ++ params

  lazy val baseProject = Project(
    id = "strangeloop2012-analytics",
    base = file("."),
    settings = projectSettings,
    aggregate = Seq(slidesIndexer)
  )

  lazy val slidesIndexer = Project(
    id = "slides-indexer",
    base = file("slides-indexer"),
    settings = projectSettings ++ Seq(
      libraryDependencies ++= Seq(tikaCore,tikaParser,luceneCore,luceneAnalyzer,jgit)
    )
  )


}