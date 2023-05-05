package models

import play.api.libs.json.{Json, OFormat}

case class Team(
                 id:Option[Int],
                 team:String)

object Team
{
  implicit val formatter: OFormat[Team] = Json.format[Team]
}
