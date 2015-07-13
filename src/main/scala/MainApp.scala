import dispatch.Http
import info.fotm.api.models._
import US.EnUS
import info.fotm.api._
import info.fotm.api.models._
import scala.concurrent.Await
import scala.concurrent.duration._

object MainApp extends App {
  val api = new BattleNetAPI(US, "vntnwpsguf4pqak7e8y7tgn35795fqfj", EnUS).WoW

  val jsonFuture = api.leaderboard(Twos)

  val json = Await.result(jsonFuture, Duration.Inf)
  println(json)

  Http.shutdown()

}
