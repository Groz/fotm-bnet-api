package info.fotm.api

case class BattleNetAPISettings(userAgent: Option[String], cache: Boolean)

object BattleNetAPISettings {
  implicit val default = BattleNetAPISettings(Some("Fotm Battle.net API"), cache = false)
}
