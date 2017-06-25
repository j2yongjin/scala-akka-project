package application

import akka.actor.{ActorSystem, Props}
import example.ParentActor

/**
  * Created by yjlee on 2017-06-25.
  */
object ActorHierarchy  extends  App{

  val mySystem = ActorSystem("mySystem");  // 액터 시스템 생성

  val parent = mySystem.actorOf(Props[ParentActor] ,"parent")

  parent ! "create"
  parent ! "create"

  Thread.sleep(1000)

  parent ! "hi"
  Thread.sleep(1000)
  parent ! "stop"
  Thread.sleep(1000)
  mySystem.terminate()




}
