name := "akka_cluster"
version := "1.0"
scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"


libraryDependencies ++= Seq(
   "com.typesafe.akka" %% "akka-actor" % "2.4.0",
   "com.typesafe.akka" %% "akka-remote" % "2.4.0",
    "com.typesafe.akka" %% "akka-cluster" % "2.4.0",
  "com.typesafe.akka" %% "akka-persistence" % "2.4.0",
    "com.typesafe.akka" %% "akka-cluster-metrics" % "2.4.0",
    "com.typesafe.akka" %% "akka-cluster-tools" % "2.4.0",
    "com.typesafe.akka" %% "akka-multi-node-testkit" % "2.4.0"


//   "com.typesafe.akka" % "akka-http-core_2.11" % "3.0.0-RC1",
//   "com.typesafe.akka" % "akka-http-spray-json_2.11" % "3.0.0-RC1"
)
