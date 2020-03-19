name := "csrf_foo"

version := "1.0"

lazy val `csrf_foo` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc ,
  ehcache ,
  ws ,
  specs2 % Test ,
  "mysql" % "mysql-connector-java" % "8.0.13",
  "com.h2database"  %  "h2"                           % "1.4.197", // your jdbc driver here
  "org.scalikejdbc" %% "scalikejdbc"         % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-config"  % "3.3.2",
  "org.scalikejdbc" %% "scalikejdbc-play-dbapi-adapter" % "2.7.0-scalikejdbc-3.3",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.7.0-scalikejdbc-3.3",
  "org.skinny-framework" %% "skinny-orm"      % "3.0.0",
  "ch.qos.logback"  %  "logback-classic"    % "1.2.+",
  guice
)

initialCommands := """
import scalikejdbc._
import skinny.orm._, feature._
import org.joda.time._
skinny.DBSettings.initialize()
implicit val session = AutoSession
"""
