package Dao

import models.{MatchData, MatchDetails}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.{JdbcProfile, JdbcType}
import slick.lifted.ProvenShape

import java.time.LocalDate
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}



class MatchDetailsDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(
  implicit ec: ExecutionContext) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class MatchTable(tag: Tag) extends Table[MatchDetails](tag, "Match_details") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey)
    def city: Rep[String] = column[String]("city")
    def date: Rep[LocalDate] = column[LocalDate]("date")
    def MoM: Rep[String] = column[String]("playerOfMatch")
    def venue: Rep[String] = column[String]("venue")
    def team1: Rep[String] = column[String]("team1")
    def team2: Rep[String] = column[String]("team2")
    def toss_win: Rep[String] = column[String]("toss_Winner")
    def toss_decision: Rep[String] = column[String]("toss_Decision")
    def winner: Rep[String] = column[String]("winner")
    def merged_result: Rep[String] = column[String]("combined_Result")
    def eliminator: Rep[String] = column[String]("eliminator")
    def umpire1: Rep[String] = column[String]("umpire1")
    def umpire2: Rep[String] = column[String]("umpire2")
    override def * : ProvenShape[MatchDetails] = (id, city, date,MoM,venue,team1,team2,toss_win,toss_decision,winner,merged_result,eliminator,umpire1,umpire2) <> ((MatchDetails.apply _).tupled, MatchDetails.unapply)
  }

  private val matchTableQuery =TableQuery[MatchTable]


  def insert(matchData: MatchDetails): Future[Unit] =
    db.run(DBIO.seq(matchTableQuery.schema.createIfNotExists, matchTableQuery += matchData))

  def fetchAll(): Future[Seq[MatchDetails]] = db.run(matchTableQuery.result)

  def getmatch(id: Int): Future[Seq[MatchDetails]] =
    db.run(matchTableQuery.filter(_.id === id).result)

  def getMatchDetail(id: Int): Future[Seq[MatchDetails]] =
    db.run(matchTableQuery.filter(_.id === id).result)

  def getmatchesbyteam(team: String): Future[Seq[MatchDetails]] =
    db.run(matchTableQuery.filter(_.team1 === team).result)

}

