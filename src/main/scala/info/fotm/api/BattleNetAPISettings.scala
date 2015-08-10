package info.fotm.api

case class BattleNetAPISettings(userAgent: Option[String], cache: Boolean, timeoutInMs: Int)

object BattleNetAPISettings {
  implicit val default = BattleNetAPISettings(Some("Fotm Battle.net API"), cache = false, timeoutInMs = 40 * 1000)
}
