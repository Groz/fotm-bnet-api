package info.fotm.api.models

sealed abstract class Region(val root: String, val slug: String) extends Serializable

sealed abstract class Locale[+R <: Region](val code: String) extends Serializable
case object DefaultLocale extends Locale[Nothing]("en_US")

case object US extends Region("us.api.battle.net", "us") {
  case object EnUS extends Locale[US.type]("en_US")
  case object EsMX extends Locale[US.type]("es_MX")
  case object PtBR extends Locale[US.type]("pt_BR")
}

case object Europe extends Region("eu.api.battle.net", "eu") {
  case object EnGB extends Locale[Europe.type]("en_GB")
  case object EsES extends Locale[Europe.type]("es_ES")
  case object FrFR extends Locale[Europe.type]("fr_FR")
  case object RuRU extends Locale[Europe.type]("ru_RU")
  case object DeDE extends Locale[Europe.type]("de_DE")
  case object PtPT extends Locale[Europe.type]("pt_PT")
  case object ItIT extends Locale[Europe.type]("it_IT")
}

case object Korea extends Region("kr.api.battle.net", "kr") {
  case object KoKR extends Locale[Korea.type]("ko_KR")
}

case object Taiwan extends Region("tw.api.battle.net", "tw") {
  case object ZhTW extends Locale[Korea.type]("zh_TW")
}

case object China extends Region("api.battlenet.com.cn", "cn") {
  case object ZhCN extends Locale[China.type]("zh_CN")
}

