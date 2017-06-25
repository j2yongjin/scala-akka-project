package application

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration.Duration

import server.{SimpleHandler, TcpEchoHandler, TcpServerManager}

import scala.concurrent.duration.FiniteDuration

/**
  * Created by yjlee on 2017-06-25.
  */
object TcpServerApplication extends App{

  val config = ConfigFactory.parseString("akka.loglevel = DEBUG")
  implicit val system = ActorSystem("TcpServer", config)

  try run()
  finally  system.terminate()

  def run() : Unit = {
    import akka.actor.ActorDSL._

    val watcher = inbox()
    watcher.watch(system.actorOf(Props(classOf[TcpServerManager], classOf[TcpEchoHandler]), "echo"))
    watcher.watch(system.actorOf(Props(classOf[TcpServerManager], classOf[SimpleHandler]), "simple"))
    watcher.receive(Duration.create(5,TimeUnit.MINUTES))

  }

}
