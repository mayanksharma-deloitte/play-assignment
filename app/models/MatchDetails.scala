package models

import play.api.libs.json.{Json, OFormat}

import java.time.LocalDate
case class MatchDetails(
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
                         umpire2:String
                       )

  object MatchData {
    implicit val formatter: OFormat[MatchDetails] = Json.format[MatchDetails]
}
