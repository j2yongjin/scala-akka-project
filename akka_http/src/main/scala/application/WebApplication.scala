package application

import akka.http.javadsl.settings.ServerSettings
import com.typesafe.config.ConfigFactory

/**
  * Created by yjlee on 2017-06-26.
  */
class WebApplication  extends  App{

  WebServerApplication.startServer("localhost" , 80)

}
