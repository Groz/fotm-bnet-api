package info.fotm.api

import regions._

import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{Await, Future, blocking}
import scala.concurrent.duration._
import scala.io.Source


case class ApiKey(key: String)

// https://dev.battle.net/io-docs
class Api[R <: Region](region: R, apiKey: ApiKey) {

  import scala.concurrent.ExecutionContext.Implicits.global

  def fetch(relativePath: String, args: String = "", locale: Locale[R] = DefaultLocale): Future[JsValue] = Future {
    val separator = if (relativePath.contains("?")) "&" else "?"
    val url = s"https://${region.root}/$relativePath${separator}locale=${locale.code}&apikey=${apiKey.key}"

    blocking {
      val html = Source.fromURL(url, "UTF-8")
      Json.parse(html.mkString)
    }
  }

  lazy implicit val achievementsReads = Json.reads[Achievements]
  lazy implicit val appearanceReads = Json.reads[Appearance]
  lazy implicit val charProfileReads = Json.reads[CharacterProfile]

  def fetchCharacterProfile(realm: String, characterName: String): Future[CharacterProfile] = {
    fetch(s"/wow/character/$realm/$characterName?fields=appearance").map(json => json.as[CharacterProfile])
  }
}

/*
{
    "lastModified": 1410118946000,
    "name": "Armory",
    "realm": "Silvermoon",
    "battlegroup": "Misery",
    "class": 1,
    "race": 7,
    "gender": 0,
    "level": 70,
    "achievementPoints": 16215,
    "thumbnail": "cerchio-del-sangue/134/114862470-avatar.jpg",
    "calcClass": "Z",
    "totalHonorableKills": 23185
}
*/
case class CharacterProfile(
  lastModified: Long,
  name: String,
  realm: String,
  battlegroup: String,
  `class`: Int,
  race: Int,
  gender: Int,
  level: Int,
  achievementPoints: Long,
  thumbnail: String,
  calcClass: String,
  totalHonorableKills: Long,
  achievements: Option[Achievements],
  appearance: Option[Appearance]
)

/*
achievements": {
      "achievementsCompleted": [6, 7, 8],
      "achievementsCompletedTimestamp": [1224083820000, 1224083820000, 1224083820000]
      "criteria": [85, 87, 88],
      "criteriaQuantity": [1, 1, 1],
      "criteriaTimestamp": [1350482474000, 1350482474000, 1350482474000],
      "criteriaCreated": [1350482474000, 1350482474000, 1350482474000],
*/
case class Achievements(
  achievementsCompleted: List[Long],
  achievementsCompletedTimestamp: List[Long],
  criteria: List[Int],
  criteriaQuantity: List[Long],
  criteriaTimestamp: List[Long],
  criteriaCreated: List[Long]
)

/*
    "appearance": {
        "faceVariation": 6,
        "skinColor": 1,
        "hairVariation": 4,
        "hairColor": 0,
        "featureVariation": 1,
        "showHelm": false,
        "showCloak": true
    },
*/
case class Appearance(
  faceVariation: Int,
  skinColor: Int,
  hairVariation: Int,
  hairColor: Int,
  featureVariation: Int,
  showHelm: Boolean,
  showCloak: Boolean
)
