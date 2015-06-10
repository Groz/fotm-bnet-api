import info.fotm.api._
import info.fotm.api.regions._
import scala.concurrent.Await
import scala.concurrent.duration._
import play.api.libs.json.{JsValue, Json}

object MainApp extends App {
  val api = new Api(Europe, ApiKey("vntnwpsguf4pqak7e8y7tgn35795fqfj"))

  val jsonFuture = api.fetchCharacterProfile("Silvermoon", "Armory")

  val json = Await.result(jsonFuture, Duration.Inf)
  println(json)

}
