package server

import akka.actor.{Actor, ActorLogging}

/**
  * Created by yjlee on 2017-06-25.
  */
class SimpleHandler extends Actor with ActorLogging{

  import akka.io.Tcp._
  def receive = {
    case Received(data) => {
      log.info(" data : " + data.utf8String)
      sender() ! Write(data)
    }
    case PeerClosed     => context stop self
  }


}
