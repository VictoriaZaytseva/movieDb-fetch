name := "MovieDBApiAggregator"

version := "1.0"

scalaVersion := "2.12.4"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.3"
libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "1.1.3"
libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"
