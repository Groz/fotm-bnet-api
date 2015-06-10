package info.fotm.api.models

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
  achievementPoints: Int,
  thumbnail: String,
  calcClass: String,
  totalHonorableKills: Int,
  achievements: Option[Achievements],
  appearance: Option[Appearance],
  pvp: Option[PvP]
)

sealed abstract class CharacterProfileField(val code: String)
case object Achievements extends CharacterProfileField("achievements")
case object Appearance extends CharacterProfileField("appearance")
case object PvP extends CharacterProfileField("pvp")

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

/*
    "pvp": {
        "brackets": {
            "ARENA_BRACKET_2v2": {
                "slug": "2v2",
                "rating": 0,
                "weeklyPlayed": 0,
                "weeklyWon": 0,
                "weeklyLost": 0,
                "seasonPlayed": 0,
                "seasonWon": 0,
                "seasonLost": 0
            },
            "ARENA_BRACKET_3v3": {
            "ARENA_BRACKET_5v5": {
            "ARENA_BRACKET_RBG": {

 */

case class PvP(brackets: Brackets)

case class Brackets(
                     ARENA_BRACKET_2v2: BracketInfo,
                     ARENA_BRACKET_3v3: BracketInfo,
                     ARENA_BRACKET_5v5: BracketInfo,
                     ARENA_BRACKET_RBG: BracketInfo
                     )

case class BracketInfo(
                        slug: String,
                        rating: Int,
                        weeklyPlayed: Int,
                        weeklyWon: Int,
                        weeklyLost: Int,
                        seasonPlayed: Int,
                        seasonWon: Int,
                        seasonLost: Int
                        )
