package Dao

import models.{winningTeams}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class winningTeamsDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(
  implicit ec: ExecutionContext) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class winningTeamsTable(tag: Tag) extends Table[winningTeams](tag, "Teams_Win") {
    def id: Rep[Option[Int]] = column[Option[Int]]("id", O.PrimaryKey,O.AutoInc)
    def teamname: Rep[String] = column[String]("team")
    def wins:Rep[Int]=column[Int]("wins")

    override def * : ProvenShape[winningTeams] = (id,teamname,wins) <> ((winningTeams.apply _).tupled, winningTeams.unapply)
  }


  private val teamsTableQuery =TableQuery[winningTeamsTable]


  def insert(teamswin: winningTeams): Future[Unit] =
    db.run(DBIO.seq(teamsTableQuery.schema.createIfNotExists, teamsTableQuery += teamswin))

  def getmatch(team: String): Future[Seq[winningTeams]] =
    db.run(teamsTableQuery.filter(_.teamname === team).result)

  def fetchAll(): Future[Seq[winningTeams]] = db.run(teamsTableQuery.result)



}