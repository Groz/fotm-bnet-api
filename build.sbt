name := "fotm-bnet-api"

version := "0.1"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.apache.logging.log4j" % "log4j-core" % "2.4",
  "com.ning" % "async-http-client" % "1.9.31",
  "com.typesafe.play" %% "play-json" % "2.4.3",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.3",
  "org.scalatest" %% "scalatest" % "2.2.5" % "test"
)
