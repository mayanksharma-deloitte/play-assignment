package modules

import Dao.MatchDetailsDao
import akka.NotUsed
import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.javadsl.{FileIO, Sink}
import akka.stream.scaladsl.{Flow, Framing}
import akka.util.ByteString
import com.google.inject.AbstractModule
import com.typesafe.config.Config
import modules.actor.ReaderActor
import play.api.libs.concurrent.AkkaGuiceSupport

import javax.inject.Inject
import scala.io.Source
import scala.reflect.io.File

 class injection  extends AbstractModule with AkkaGuiceSupport {

  override def configure(): Unit = {
    bind(classOf[initialisation]).asEagerSingleton()

    }

}
