package application

//import akka.http.javadsl.model.ContentTypes
//import akka.http.javadsl.server.{HttpApp, Route}
//import akka.http.scaladsl.model.HttpEntity

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.HttpApp
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.settings.ServerSettings
import com.typesafe.config.ConfigFactory


/**
  * Created by yjlee on 2017-06-26.
  */
object WebServerApplication extends  HttpApp{


  override def routes : Route =
    path("hello") {
        get{
           complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,"<h1> hello to akka webserver </h1>"))
        }
      }

  override protected def postHttpBindingFailure(cause: Throwable): Unit = {
    println(s"The server could not be started due to $cause")
  }

}

// Starting the server
WebServerApplication.startServer("localhost", 80, ServerSettings(ConfigFactory.load))
