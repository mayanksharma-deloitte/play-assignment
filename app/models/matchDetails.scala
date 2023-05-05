package models

import play.api.libs.json.{Json, OFormat}

import java.time.LocalDate
import scala.util.matching.Regex.MatchData
case class matchDetails(
                      id:Int,
                      city:String,
                      date:LocalDate,
                      playerOfMatch:String,
                      venue:String,
                      team1:String,
                      team2:String,
                      toss_Winner:String,
                      toss_decision:String,
                      winner:String,
                      combined_result:String,
                      eliminator:String,
                      umpire1:String,
                      umpire2:String)

object matchDetails {
  implicit val formatter: OFormat[MatchData] = Json.format[MatchData]
}
