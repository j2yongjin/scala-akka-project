package application

import akka.actor.{ActorRef, ActorSystem, Props}
import example.HelloAkka

/**
  * Created by yjlee on 2017-06-25.
  */
object ActorCreateApp  extends  App{

  val mySystem = ActorSystem("mySystem");  // 액터 시스템 생성

  // 액터 시스템을 통해 나에 액터를 이름은 greeter , 타입은 ActorRef
  val helloActor : ActorRef = mySystem.actorOf(Props(new HelloAkka("hello")),name = "greeter")

  // hello actor 에게 hellow 메시지 전송
  helloActor ! "hello"

  Thread.sleep(1000)

  helloActor ! 3

  Thread.sleep(1000)

  // 액터 종료
  mySystem.terminate()



}
