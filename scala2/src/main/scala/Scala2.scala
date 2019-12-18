import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object Scala2 {

  sealed trait Foo
  case class Bar(xs: Vector[String]) extends Foo
  case class Qux(i: Int, d: Option[Double]) extends Foo

  def msg = "it's scala2"

  def some() {
    println(msg)
  }

  def processJson(json: String): Either[Error, Foo] =
    decode[Foo](json)

}
