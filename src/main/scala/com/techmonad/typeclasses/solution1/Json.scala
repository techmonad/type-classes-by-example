package com.techmonad.typeclasses.solution1

sealed trait JValue

case class JObject(obj: List[(String, JValue)]) extends JValue

case class JArray(arr: List[JValue]) extends JValue

case class JString(str: String) extends JValue

case class JBoolean(bool :Boolean) extends JValue

case class JNumber(n: Int) extends JValue

case object JNull extends JValue

object JsonWriter {

  def write(jValue: JValue): String =
    jValue match {
      case JString(str) => s""""$str""""
      case JNumber(n) => n.toString()
      case JBoolean(bool) => bool.toString
      case JNull => "null"
      case JObject(obj) =>
        val jsonString = obj.map { case (key, jValue) => s""""$key"""" + " : " + write(jValue) }.mkString(", ")
        s"""{ $jsonString }"""
      case JArray(arr) =>
        val jsonString = arr.map { jValue => write(jValue) }.mkString(" ,")
        s"""[ $jsonString ]"""
    }

  def write(value: JsonConvertible): String =
    write(value.toJson)
}


trait JsonConvertible {

  def toJson: JValue

}
