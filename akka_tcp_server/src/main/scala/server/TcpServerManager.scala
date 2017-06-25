package server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, Props, SupervisorStrategy}
import akka.io.{IO, Tcp}

/**
  * Created by yjlee on 2017-06-25.
  */
class TcpServerManager( handlerClass: Class[_] ) extends  Actor with ActorLogging{

  import akka.io.Tcp._
  import context.system

  override val supervisorStrategy = SupervisorStrategy.stoppingStrategy

  override def preStart(): Unit = {
       IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 0))
  }

  override def postRestart(thr: Throwable): Unit = context stop self



  def receive = {
     case Bound(localAddress) =>
       log.info("listening on port {}", localAddress.getPort)

     case CommandFailed(Bind(_, local, _, _, _)) =>
       log.warning(s"cannot bind to [$local]")
       context stop self

     case Connected(remote, local) =>
       log.info("received connection from {}", remote)
       val handler = context.actorOf(Props(handlerClass, sender(), remote))
       sender() ! Register(handler, keepOpenOnPeerClosed = true)
  }

}
