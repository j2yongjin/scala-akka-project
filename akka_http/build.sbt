name := "akka_http"
version := "1.0"
scalaVersion := "2.11.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= Seq(
   "com.typesafe.akka" %% "akka-actor" % "2.5.3",
   "com.typesafe.akka" %% "akka-remote" % "2.5.3",
   "com.typesafe.akka" % "akka-http_2.11" % "10.0.8"
//   "com.typesafe.akka" % "akka-http-core_2.11" % "3.0.0-RC1",
//   "com.typesafe.akka" % "akka-http-spray-json_2.11" % "3.0.0-RC1"
)
