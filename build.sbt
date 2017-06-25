name := "scala-multi-project"
version := "1.0"
scalaVersion := "2.12.1"

lazy val main_project = Project(id = "multi-scala"
, base = file(".")).aggregate(scala_protobuf,akka_tcp_server,akka_tcp_client,akka_sample)

lazy val scala_protobuf = Project(id = "scala_protobuf" , base = file("scala_protobuf")).enablePlugins()
lazy val akka_tcp_server = Project(id = "akka_tcp_server" , base = file("akka_tcp_server")).enablePlugins()
lazy val akka_tcp_client = Project(id = "akka_tcp_client" , base = file("akka_tcp_client")).enablePlugins()
lazy val akka_sample = Project(id = "akka_sample" , base = file("akka_sample")).enablePlugins()