package services

import scala.concurrent.{ExecutionContext, Future}
import json.jsonValues._
import models._
import cats.data.OptionT
import cats.instances.future._

class Fetcher(web: Web) {
  val tvShowUrl = "https://api.themoviedb.org/3/tv/top_rated?api_key=e0a1e9011765b5e6f4a7f5ffe17b1fe9"
  def tvShowDetailsUrl(id: Int) = s"https://api.themoviedb.org/3/tv/$id?api_key=e0a1e9011765b5e6f4a7f5ffe17b1fe9&append_to_response=credits"
  def tvShowEpisodesUrl(id: Int, season: Int) = s"https://api.themoviedb.org/3/tv/$id/season/$season?api_key=e0a1e9011765b5e6f4a7f5ffe17b1fe9"
  def personDetails(id: Int) = s"https://api.themoviedb.org/3/person/$id?api_key=e0a1e9011765b5e6f4a7f5ffe17b1fe9&append_to_response=credits"

  def fetchTVShows(implicit ex: ExecutionContext) : OptionT[Future, List[TVShow]] = {
    web.call(tvShowUrl).map { response =>
      response.as[TVShowResponse].results
    }
  }

  def fetchTVShowDetails(id: Int)(implicit ex: ExecutionContext): OptionT[Future, Details] = {
    web.call(tvShowDetailsUrl(id))(ex).map { response =>
      response.as[Details]
    }
  }

  def fetchEpisodes(id: Int, number: Int)(implicit ex: ExecutionContext): OptionT[Future, List[Episode]] = {
    web.call(tvShowEpisodesUrl(id, number))(ex).map { response =>
       response.as[EpisodeResponse].episodes
    }
  }

  def fetchPersonDetails(id: Int)(implicit ex: ExecutionContext): OptionT[Future, Person] = {
    web.call(personDetails(id))(ex).map { response =>
      response.as[Person]
    }
  }
}
