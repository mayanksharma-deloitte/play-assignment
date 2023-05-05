package models

import play.api.libs.json.{Json, OFormat}

case class winningTeams(
                         id:Option[Int],
                         team:String,
                         wins:Int)

object winningTeams
{
  implicit val formatter: OFormat[winningTeams] = Json.format[winningTeams]
}