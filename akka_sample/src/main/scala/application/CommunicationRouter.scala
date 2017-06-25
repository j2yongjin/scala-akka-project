package application

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.routing.Router

/**
  * Created by yjlee on 2017-06-25.
  */
object CommunicationRouter extends App{

  val router = mySystem.myActorSystem.actorOf(Props[Router], "router")
  router ! "Hi."
  router ! "I'm talking to you!"
  Thread.sleep(1000)
  router ! "stop"
  Thread.sleep(1000)
  mySystem.myActorSystem.terminate

}

object mySystem {
  lazy val myActorSystem = ActorSystem("exampleSystem")
}

class StringPrinter extends Actor with ActorLogging{

  def receive = {
    case msg => log.info(s"child got message '$msg'")
  }
  override def preStart(): Unit = log.info(s"child about to start.")
  override def postStop(): Unit = log.info(s"child just stopped.")
}


class Router extends Actor {
  var i = 0
  val children = for (_ <- 0 until 4) yield context.actorOf(Props[StringPrinter])
  def receive = {
    case "stop" => context.stop(self)
    case msg =>
      children(i) forward msg
      i = (i + 1) % 4
  }
}

