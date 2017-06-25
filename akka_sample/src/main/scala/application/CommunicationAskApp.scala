package application

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}

/**
  * Created by yjlee on 2017-06-25.
  */
object CommunicationAskApp extends App{

  val master = mySystem.myActorSystem.actorOf(Props[Master],"master")
  master ! "start"
  Thread.sleep(5000)

  mySystem.myActorSystem.terminate();

}

object mySystem {
  lazy val myActorSystem = ActorSystem("exampleSystem")
}

class Pongy extends Actor with  ActorLogging{

  def receive = {
    case "ping" =>
      log.info(" get a ping -> pong back")
      sender ! "pong"
      context.stop(self)
  }

  override def postStop() = log.info("pongy going down")
}

class Pingy extends Actor with ActorLogging{
  def receive = {
    case pongyRef: ActorRef =>
      pongyRef ! "ping"

    case "pong" =>
      log.info("get a pong back")
      context.stop(self)
  }

  override def postStop() = log.info("ping going down")
}

class Master extends Actor  with ActorLogging{

  val pingy = mySystem.myActorSystem.actorOf(Props[Pingy])
  val pongy = mySystem.myActorSystem.actorOf(Props[Pongy])

  def receive = {
    case "start" =>
      pingy ! pongy
  }
  override def postStop() = log.info("master going down")

}
