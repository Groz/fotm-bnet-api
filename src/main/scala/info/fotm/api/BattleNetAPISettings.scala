package info.fotm.api

import dispatch.Http

case class BattleNetAPISettings(userAgent: Option[String], cache: Boolean, timeoutInMs: Int) {
  val http = Http.configure(_
    .setConnectTimeout(timeoutInMs)
    .setReadTimeout(timeoutInMs)
    .setRequestTimeout(timeoutInMs)
  )
}

object BattleNetAPISettings {
  implicit val default = BattleNetAPISettings(Some("Fotm Battle.net API"), cache = false, timeoutInMs = 30 * 1000)
}
