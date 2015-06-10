package info.fotm.api

import models._
import regions._

import play.api.libs.json.{Reads, JsValue, Json}
import scala.concurrent.{Future, blocking}
import scala.io.Source


// https://dev.battle.net/io-docs
class BattleNetAPI[R <: Region](region: R, key: String, locale: Locale[R] = DefaultLocale) { self =>

  import scala.concurrent.ExecutionContext.Implicits.global

  def fetchJson(relativePath: String): Future[JsValue] = Future {
    val delim = if (relativePath.contains("?")) "&" else "?"
    val url = s"https://${region.root}/$relativePath${delim}locale=${locale.code}&apikey=$key"

    blocking {
      val html = Source.fromURL(url, "UTF-8")
      Json.parse(html.mkString)
    }
  }

  def fetch[T](relativePath: String)(implicit reads: Reads[T]): Future[T] = fetchJson(relativePath).map(_.as[T])

  object WoW {
    lazy implicit val achievementsReads = Json.reads[Achievements]
    lazy implicit val appearanceReads = Json.reads[Appearance]
    lazy implicit val bracketsInfoReads = Json.reads[BracketInfo]
    lazy implicit val bracketsReads = Json.reads[Brackets]
    lazy implicit val pvpReads = Json.reads[PvP]
    lazy implicit val charProfileReads = Json.reads[CharacterProfile]

    def characterProfile(realm: String, characterName: String, fields: List[CharacterProfileField] = Nil): Future[CharacterProfile] = {
      val relativePath = s"/wow/character/$realm/$characterName"
      val fieldString = fields.map(_.code).mkString(",")
      val path = if (fieldString.isEmpty) relativePath else relativePath+s"?fields=$fieldString"
      fetch[CharacterProfile](path)
    }

    lazy implicit val leaderboardRowReads = Json.reads[LeaderboardRow]
    lazy implicit val leaderboardsReads = Json.reads[Leaderboard]

    def leaderboard(bracket: Bracket): Future[Leaderboard] = {
      fetch[Leaderboard](s"/wow/leaderboard/${bracket.slug}")
    }
  }

}

