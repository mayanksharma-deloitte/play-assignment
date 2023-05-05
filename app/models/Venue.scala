package models

import play.api.libs.json.{Json, OFormat}

case class Venue(id:Option[Int],venueName:String)

object Venue
{
  implicit val formatter: OFormat[Venue] = Json.format[Venue]
}
