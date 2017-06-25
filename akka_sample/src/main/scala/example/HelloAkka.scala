package example

import akka.actor.{Actor, ActorLogging}

/**
  * Created by yjlee on 2017-06-25.
  */
class HelloAkka(val hello : String) extends Actor with ActorLogging{

  def receive = {
    case "hello" =>
      log.info(s"Receive a $hello!")
    case msg =>
      log.info(s"Unexpected message $msg")
      context.stop(self)

  }

}
