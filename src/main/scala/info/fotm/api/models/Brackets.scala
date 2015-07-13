package info.fotm.api.models

sealed abstract class Bracket(val size: Int) extends Serializable {
  lazy val slug: String = s"${size}v$size"
}

case object Twos extends Bracket(2)
case object Threes extends Bracket(3)
case object Fives extends Bracket(5)
case object Rbg extends Bracket(10) {
  override lazy val slug = "rbg"
}
