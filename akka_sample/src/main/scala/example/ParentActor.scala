package example

import akka.actor.{Actor, ActorLogging, Props}

/**
  * Created by yjlee on 2017-06-25.
  */
class ParentActor extends Actor with ActorLogging{

  def receive = {

    case "create" =>
      context.actorOf(Props[ChildActor])   // 자식 액터를 생성한다.
      log.info(" create new Child")

    case "hi" =>
      log.info(" hi")
      for(c <- context.children) c! "hi"  // hi를 받으면 자식 엑터들에게 hi를 전달한다.

    case "stop" =>
      log.info("parent stopping")
      context.stop(self)   // 부모 엑터가 종료되면 자동으로 자식 액터가 종료된다.
  }



}
