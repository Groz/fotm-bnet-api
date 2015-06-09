import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{Await, Future, blocking}
import scala.concurrent.duration._
import scala.io.Source

import scala.concurrent.ExecutionContext.Implicits.global

sealed abstract class Region(val root: String)
sealed abstract class Locale[+R <: Region](val code: String)
case object DefaultLocale extends Locale[Nothing]("en_US")

sealed abstract class US extends Region("us.api.battle.net")
case object US extends US
case object EnUS extends Locale[US]("en_US")
case object EsMx extends Locale[US]("es_MX")
case object PtBr extends Locale[US]("pt_BR")

sealed abstract class Europe extends Region("eu.api.battle.net")
case object Europe extends Europe
case object EnGB extends Locale[Europe]("en_GB")
case object EsEs extends Locale[Europe]("es_ES")
case object FrFr extends Locale[Europe]("fr_FR")
case object RuRu extends Locale[Europe]("ru_RU")
case object DeDe extends Locale[Europe]("de_DE")
case object PtPt extends Locale[Europe]("pt_PT")
case object ItIt extends Locale[Europe]("it_IT")

sealed abstract class Korea extends Region("kr.api.battle.net")
case object Korea extends Korea
case object KoKR extends Locale[Korea]("ko_KR")

sealed abstract class Taiwan extends Region("tw.api.battle.net")
case object Taiwan extends Taiwan
case object TwTw extends Locale[Korea]("zh_TW")

sealed abstract class China extends Region("api.battlenet.com.cn")
case object China extends China
case object ZhCN extends Locale[China]("zh_CN")

case class ApiKey(key: String)

class Api[R <: Region](region: R, apiKey: ApiKey) {

  def fetch(methodWithParams: String, locale: Locale[R] = DefaultLocale): Future[JsValue] = Future {
    val url = s"https://${region.root}/$methodWithParams?locale=${locale.code}&apikey=${apiKey.key}"

    blocking {
      val html = Source.fromURL(url, "UTF-8")
      Json.parse(html.mkString)
    }
  }

}

object MainApp extends App {

  val apikey = ApiKey("vntnwpsguf4pqak7e8y7tgn35795fqfj")

  val api = new Api(Europe, apikey)

  val jsonFuture = api.fetch("wow/achievement/2144", RuRu)

  val json = Await.result(jsonFuture, Duration.Inf)
  println(json)

}
