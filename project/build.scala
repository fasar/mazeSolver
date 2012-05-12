import ch.epfl.lamp.compiler.msil.Assembly
import sbt._
import Keys._
import sbtassembly._

object BuildSettings {
  val buildOrganization = "fsart.maze"
  val buildVersion      = "1.0"
  val buildScalaVersion = "2.9.1"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version      := buildVersion,
    scalaVersion := buildScalaVersion,
    scalacOptions:= Seq("-unchecked", "-deprecation")
  )

}

//resolvers += "Scala-Tools Maven2 Snapshots Repository" at "http://scala-tools.org/repo-snapshots"
//resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"


object Dependencies {
  val scalatest	= "org.scalatest" % "scalatest_2.9.1" % "1.6.1" % "test"
  val scalaSwing = "org.scala-lang" % "scala-swing" % "2.9.1" 
}


object MazeBuild extends Build {
  import Dependencies._
  import BuildSettings._

  // Sub-project specific dependencies
  val commonDeps = Seq (
    scalatest
  )

  lazy val root = Project (
    "maze",
    file("."),
    settings = buildSettings
  ) aggregate (common, maze_console, maze_gui, mazebmp_extractor)
  
  lazy val common = Project (
    "common",
    file("maze-common"),
    settings = buildSettings ++ Seq(libraryDependencies ++= commonDeps)
  )
  
  lazy val maze_console = Project (
    "maze-console",
    file("application/maze-console"),
    settings = buildSettings ++ Seq(libraryDependencies ++= commonDeps)
  ) dependsOn(common) settings(sbtassembly.Plugin.assemblySettings: _* )


  // Plugin.assemblySettings is from https://github.com/eed3si9n/sbt-assembly
  // show assembly:[tab]
  lazy val maze_gui = Project (
    "maze-gui",
    file("application/maze-gui"),
    settings = buildSettings ++ Seq(libraryDependencies ++= commonDeps) ++ Seq(libraryDependencies ++= Seq(scalaSwing) )
  ) dependsOn(common) settings(sbtassembly.Plugin.assemblySettings: _* )


  lazy val mazebmp_extractor = Project (
    "mazebmp-extractor",
    file("application/mazebmp-extractor"),
    settings = buildSettings ++ Seq(libraryDependencies ++= commonDeps) ++ Seq(libraryDependencies ++= Seq(scalaSwing))
  ) dependsOn(common) settings(sbtassembly.Plugin.assemblySettings: _* )
}




