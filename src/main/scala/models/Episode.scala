package models

case class Episode (id: Int, name: String, vote_average: Double) { override def toString = { name }}
