package application

/**
  * Created by yjlee on 2017-06-25.
  */
import akka.actor.{Actor, ActorRef, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import java.net.InetSocketAddress

import client.TcpClient


object TcpClientApplication extends App{

  val client = system.actorOf(TcpClient.props(listen, testActor), "client1")

  client ! ByteString("hello")
  client ! "close"


}
