package services

import cats.Apply
import cats.data.OptionT
import models._
import scala.concurrent.{ExecutionContext, Future}
import cats.syntax.all._
import cats.instances.future._
import cats.instances.option._

class Aggregator(parser: Fetcher) {

  def getData(implicit ex: ExecutionContext): Future[Option[DataToPrint]]  = {
   parser.fetchTVShows.map{ shows =>
     val tvShows = shows.take(10)
     val showDetails = Future.traverse(tvShows)(tvsShow => getDetails(tvsShow.id).value)
     val episodes = showDetails.flatMap(details => Future.traverse(details.flatten)(data => getEpisodes(data.id, data.number_of_seasons))).map(_.flatten)
     val people = showDetails.flatMap(details => Future.traverse(details.flatten)(data => getPeople(data.cast))).map(_.flatten)
      Apply[Future].map2(episodes, people)((eps, ppl) =>{
       DataToPrint(tvShows, eps.sortBy(_.vote_average).takeRight(10),  ppl.sortBy(_.popularity).takeRight(10))
     })
   }.value.flatMap(value => value.sequence)
  }

  def getDetails(id: Int)(implicit ex: ExecutionContext): OptionT[Future, Details] = {
    parser.fetchTVShowDetails(id)
  }

  def getEpisodes(id: Int, numberOfSeasons: Int)(implicit ex: ExecutionContext): Future[List[Episode]] = {
    val range = (1 to numberOfSeasons).toList
    val request = Future.traverse(range)(parser.fetchEpisodes(id, _).value)
    request.map(_.flatten.flatten)
  }

  def getPeople(stars: List[Star])(implicit ex: ExecutionContext): Future[List[Person]] = {
    val request = Future.traverse(stars.map(_.id))(parser.fetchPersonDetails(_).value)
    request.map(_.flatten)
  }
}
