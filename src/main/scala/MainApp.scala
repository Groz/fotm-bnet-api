import info.fotm.api._
import info.fotm.api.models._
import scala.concurrent.Await
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

object MainApp extends App {
  val bnet = new BattleNetAPI(US, "vntnwpsguf4pqak7e8y7tgn35795fqfj")
  val api = bnet.WoW

  while (true) {
    val jsonFuture = api.leaderboard(Twos)

    jsonFuture.onComplete { r => println(r.isSuccess) }

    Await.ready(jsonFuture, Duration.Inf)
  }

  bnet.close()
}
