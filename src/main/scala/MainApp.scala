import info.fotm.api._
import info.fotm.api.models.{Twos, Appearance, PvP}
import info.fotm.api.regions.Europe.{RuRU, EsES}
import info.fotm.api.regions.US.EnUS
import info.fotm.api.regions._
import scala.concurrent.Await
import scala.concurrent.duration._
import play.api.libs.json.{JsValue, Json}

object MainApp extends App {
  val api = new BattleNetAPI(US, "vntnwpsguf4pqak7e8y7tgn35795fqfj", EnUS).WoW

  val jsonFuture = api.leaderboard(Twos)

  val json = Await.result(jsonFuture, Duration.Inf)
  println(json)

}
