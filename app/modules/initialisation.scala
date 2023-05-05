package modules

import Dao.{MatchDetailsDao, TeamDao, VenueDao, WinningTeamsDao}
import akka.actor.{Actor, ActorSystem, PoisonPill, Props, Terminated}
import models.{MatchDetails, winningTeams}
import modules.actor.ReaderActor

import javax.inject.{Inject, Singleton}
import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.io.Source

@Singleton
class initialisation @Inject()(matchDataDao: MatchDetailsDao, teamsDao: TeamDao, venueDao: VenueDao, teamsWinDao: WinningTeamsDao) {

  def populatetables(matchDataDao: MatchDetailsDao, teamsWinDao: WinningTeamsDao, teamsDao: TeamDao): Unit ={

    val Matchlistfuture = matchDataDao.fetchAll()
    val teams = collection.mutable.Set[String]()
    val winners=collection.mutable.ListBuffer[String]()
    val matchList: Seq[MatchDetails] = Await.result(Matchlistfuture, Duration.Inf)
    for(m<-matchList)
      {
        teams += m.team1
        winners += m.winner
      }

      for(t<-teams) {
        var count=0;
        {
          for(w<-winners)
            {
              if(w.equalsIgnoreCase(t)) {
                count+=1
              }
            }
            teamsWinDao.insert(new winningTeams(None,t,count))
        }
      }
  }
  val source = Source.fromFile("app/resources/IPL.csv")
  val lines = source.getLines()
  val linewithoutheader=lines.drop(1)

  val system = ActorSystem("FileReaderSystem")
  val fileReaderActor = system.actorOf(Props(new ReaderActor(matchDataDao,teamsDao, venueDao)), "fileReaderActor")

  system.actorOf(Props(new Actor {
    context.watch(fileReaderActor)

    def receive = {
      case Terminated(`fileReaderActor`) =>

        populatetables(matchDataDao,teamsWinDao,teamsDao)
    }
  }))

  var bool=false
  while (linewithoutheader.hasNext){
    val line =lines.next()
    val record = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)(?<![ ])(?![ ])")
    fileReaderActor ! record
  }

if(!lines.hasNext)
  {
    fileReaderActor!PoisonPill
  }






}
