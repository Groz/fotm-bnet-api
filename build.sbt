name := "fotm-bnet-api"

version := "0.1"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "com.ning" % "async-http-client" % "1.9.30",
  "com.typesafe.play" %% "play-json" % "2.4.0",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.3",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
