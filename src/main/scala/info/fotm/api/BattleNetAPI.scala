package info.fotm.api

import java.io.Closeable

import dispatch.{Http, as, host}
import info.fotm.api.models._
import play.api.libs.json.{JsValue, Json, Reads}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

// https://dev.battle.net/io-docs
class BattleNetAPI[R <: Region](
    region: R,
    apikey: String,
    locale: Locale[R] = DefaultLocale,
    settings: BattleNetAPISettings = BattleNetAPISettings.default)
  extends Closeable { self =>

  val timeoutInMs = settings.timeoutInMs

  val headers = Map("Accept-Encoding" -> "gzip, deflate")

  val noCacheHeaders = Map(
    "Cache-Control" -> "no-cache, no-store, must-revalidate",
    "Pragma" -> "no-cache",
    "Expires" -> "0")

  def fetchJson(relativePath: Seq[String], params: Map[String, String] = Map.empty): Future[JsValue] = {
    val path = (host(region.root) /: relativePath) { _ / _ }

    val req = path.secure <<? Map("locale" -> locale.code, "apikey" -> apikey) <<? params

    val uaHeader = settings.userAgent.map("User-Agent" -> _)

    val cacheHeaders = if (!settings.cache) noCacheHeaders else Map.empty

    val withHeaders = req <:< headers <:< uaHeader.toMap <:< cacheHeaders

    settings.http(withHeaders OK as.String).map(Json.parse)
  }

  def fetch[T](relativePaths: Seq[String], params: Map[String, String] = Map.empty)(implicit reads: Reads[T]): Future[T] =
    fetchJson(relativePaths, params).map(_.as[T])

  object WoW {
    lazy implicit val achievementsReads = Json.reads[Achievements]
    lazy implicit val appearanceReads = Json.reads[Appearance]
    lazy implicit val bracketsInfoReads = Json.reads[BracketInfo]
    lazy implicit val bracketsReads = Json.reads[BracketsInfo]
    lazy implicit val pvpReads = Json.reads[PvP]
    lazy implicit val charProfileReads = Json.reads[CharacterProfile]

    def characterProfile(realm: String, characterName: String, fields: List[CharacterProfileField] = Nil): Future[CharacterProfile] = {
      val relativePath = s"/wow/character/$realm/$characterName"
      val fieldString = fields.map(_.code).mkString(",")
      val args: Map[String, String] = if (fieldString.isEmpty) Map("fields" -> fieldString) else Map()
      fetch[CharacterProfile](Seq("wow", "character", realm, characterName), args)
    }

    lazy implicit val leaderboardRowReads = Json.reads[LeaderboardRow]
    lazy implicit val leaderboardsReads = Json.reads[Leaderboard]

    def leaderboard(bracket: Bracket): Future[Leaderboard] = {
      fetch[Leaderboard](Seq("wow", "leaderboard", bracket.slug))
    }
  }

  def close(): Unit = {}
}
