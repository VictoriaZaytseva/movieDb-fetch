package models

case class Person(id: Int, name: String, popularity: Double){ override def toString = { name } }
