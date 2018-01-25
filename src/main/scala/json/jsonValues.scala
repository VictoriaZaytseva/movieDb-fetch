package json

import models._
import play.api.libs.json._
import play.api.libs.functional.syntax._

object jsonValues {

  implicit val tvshowReads: Reads[TVShow] = (
     (JsPath \ "id").read[Int] and
      (JsPath \ "name").read[String]
    )(TVShow.apply _)

  implicit val tvshowResponseReads: Reads[TVShowResponse] = (
    (JsPath \ "page").read[Int] and
      (JsPath \ "total_results").read[Int] and
      (JsPath \ "total_pages").read[Int] and
      (JsPath \ "results").read[List[TVShow]]
    )(TVShowResponse.apply _)

  implicit val seasonReads: Reads[Season] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "number").read[Int]
    )(Season.apply _)

  implicit val episodeReads: Reads[Episode] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "name").read[String] and
        (JsPath \ "vote_average").read[Double]
    )(Episode.apply _)

  implicit val episodeResponseReads: Reads[EpisodeResponse] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "episodes").read[List[Episode]]
    )(EpisodeResponse.apply _)

  implicit val starReads: Reads[Star] = Json.format[Star]
  implicit val personReads: Reads[Person] = Json.format[Person]

  implicit val detailReads:  Reads[Details] = (
    (JsPath \ "id").read[Int] and
      (JsPath \ "number_of_seasons").read[Int] and
      (JsPath \ "credits" \ "cast").read[List[Star]]
    )(Details.apply _)
}
