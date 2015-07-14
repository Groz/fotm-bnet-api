import info.fotm.api._
import info.fotm.api.models._
import scala.concurrent.Await
import scala.concurrent.duration._

object MainApp extends App {
  val bnet = new BattleNetAPI(US, "vntnwpsguf4pqak7e8y7tgn35795fqfj")
  val api = bnet.WoW

  val jsonFuture = api.leaderboard(Twos)

  val json = Await.result(jsonFuture, Duration.Inf)
  println(json)

  bnet.close()
}
