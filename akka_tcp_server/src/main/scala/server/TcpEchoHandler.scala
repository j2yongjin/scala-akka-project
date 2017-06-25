package server

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.io.Tcp
import akka.util.ByteString

/**
  * Created by yjlee on 2017-06-25.
  */
class TcpEchoHandler(connection: ActorRef, remote: InetSocketAddress) extends Actor with ActorLogging{

  import Tcp._
  import TcpEchoHandler._

  context watch connection

  def receive = writing

  def writing: Receive = {
         case Received(data) =>
           connection ! Write(data, Ack(currentOffset))
           buffer(data)

         case Ack(ack) =>
           acknowledge(ack)

         case CommandFailed(Write(_, Ack(ack))) =>
           connection ! ResumeWriting
           context become buffering(ack)


         case PeerClosed =>
           if (storage.isEmpty) context stop self
           else context become closing
   }

  def buffering(nack: Int): Receive = {
       var toAck = 10
       var peerClosed = false

       {
           case Received(data)         => buffer(data)
           case WritingResumed         => writeFirst()
             case PeerClosed             => peerClosed = true
             case Ack(ack) if ack < nack => acknowledge(ack)
           case Ack(ack) =>
             acknowledge(ack)
             if (storage.nonEmpty) {
                 if (toAck > 0) {
                     // stay in ACK-based mode for a while
                     writeFirst()
                     toAck -= 1
                   } else {
                     // then return to NACK-based again
                     writeAll()
                     context become (if (peerClosed) closing else writing)
                   }
               } else if (peerClosed) context stop self
             else context become writing
        }
   }

  def closing: Receive = {
        case CommandFailed(_: Write) =>
          connection ! ResumeWriting
          context.become({

           case WritingResumed =>
             writeAll()
             context.unbecome()

           case ack: Int => acknowledge(ack)

         }, discardOld = false)


        case Ack(ack) =>
          acknowledge(ack)
          if (storage.isEmpty) context stop self
      }
     //#closing


  override def postStop(): Unit = {
       log.info(s"transferred $transferred bytes from/to [$remote]")
  }

  //#storage-omitted
  private var storageOffset = 0
  private var storage = Vector.empty[ByteString]
  private var stored = 0L
  private var transferred = 0L

  val maxStored = 100000000L
  val highWatermark = maxStored * 5 / 10
  val lowWatermark = maxStored * 3 / 10
  private var suspended = false

  private def currentOffset = storageOffset + storage.size

  //#helpers
  private def buffer(data: ByteString): Unit = {
    storage :+= data
    stored += data.size

    if (stored > maxStored) {
      log.warning(s"drop connection to [$remote] (buffer overrun)")
      context stop self

    } else if (stored > highWatermark) {
      log.debug(s"suspending reading at $currentOffset")
      connection ! SuspendReading
      suspended = true
    }
  }

  private def acknowledge(ack: Int): Unit = {
    require(ack == storageOffset, s"received ack $ack at $storageOffset")
    require(storage.nonEmpty, s"storage was empty at ack $ack")

    val size = storage(0).size
    stored -= size
    transferred += size

    storageOffset += 1
    storage = storage drop 1

    if (suspended && stored < lowWatermark) {
      log.debug("resuming reading")
      connection ! ResumeReading
      suspended = false
    }
  }
  //#helpers
  private def writeFirst(): Unit = {
    connection ! Write(storage(0), Ack(storageOffset))
  }

  private def writeAll(): Unit = {
    for ((data, i) <- storage.zipWithIndex) {
      connection ! Write(data, Ack(storageOffset + i))
    }
  }

}

object TcpEchoHandler {
    final case class Ack(offset: Int) extends Tcp.Event

    def props(connection: ActorRef, remote: InetSocketAddress): Props =
       Props(classOf[TcpEchoHandler], connection, remote)
}

