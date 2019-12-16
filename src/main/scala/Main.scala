import Scala2._

object Main {

  def circeWitoutMacros(): Unit = {
    import cats.syntax.either._
    import io.circe._, io.circe.parser._

    val json: String = """
        {
          "id": "c730433b-082c-4984-9d66-855c243266f0",
          "name": "Foo",
          "counts": [1, 2, 3],
          "values": {
            "bar": true,
            "baz": 100.001,
            "qux": ["a", "b"]
          }
        }
      """

    val doc: Json = parse(json).getOrElse(Json.Null)

    val cursor: HCursor = doc.hcursor

    val baz: Decoder.Result[Double] =
      cursor.downField("values").downField("baz").as[Double]

    val baz2: Decoder.Result[Double] =
      cursor.downField("values").get[Double]("baz")

    val secondQux: Decoder.Result[String] =
      cursor.downField("values").downField("qux").downArray.as[String]

    val reversedNameCursor: ACursor =
      cursor.downField("name").withFocus(_.mapString(_.reverse))

    val reversedName: Option[Json] = reversedNameCursor.top

    println("Result from dotty code: " + reversedName.toString)
  }

  def main(args: Array[String]): Unit = {
    println("I was compiled by dotty :)")

    circeWitoutMacros()

    val jsonStr = """{"Qux":{"i":13,"d":14.0}}"""

    println(s"Calling Scala2.processJson with $jsonStr (it used circe auto derivation).")
    val r = Scala2.processJson(jsonStr)

    println("Result from scala2 code: " + r.toString)
  }
}
