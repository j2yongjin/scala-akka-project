package example

import akka.actor.{Actor, ActorLogging}

/**
  * Created by yjlee on 2017-06-25.
  */
class ChildActor extends Actor with ActorLogging{

  def receive = {
    case "hi" =>
      val parent = context.parent
      log.info(s" my parent is $parent")
  }

  override def postStop()  {
    log.info("child stoped")
  }

}
