package Dao

import models.{Team}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TeamDao @Inject()(protected val dbConfigProvider: DatabaseConfigProvider, cc: ControllerComponents)(
  implicit ec: ExecutionContext) extends AbstractController(cc) with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  class TeamsTable(tag: Tag) extends Table[Team](tag, "Teams") {
    def id: Rep[Option[Int]] = column[Int]("id", O.PrimaryKey,O.AutoInc)
    def teamname: Rep[String] = column[String]("teams")

    override def * : ProvenShape[Team] = (id,teamname) <> ((Team.apply _).tupled, Team.unapply)
  }


  private val teamsTableQuery =TableQuery[TeamsTable]


  def insert(teams: Team): Future[Unit] =
    db.run(DBIO.seq(teamsTableQuery.schema.createIfNotExists, teamsTableQuery += teams))

  def fetchAll(): Future[Seq[Team]] = db.run(teamsTableQuery.result)


}

