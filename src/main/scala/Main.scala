import akka.stream.ActorMaterializer
import akka.actor.ActorSystem
import play.api.libs.ws._
import play.api.libs.ws.ahc._
import play.api.libs.json._
import services._

import scala.concurrent.{ExecutionContext, Future}

object Main  {


  def printTVShows(aggregator: Aggregator)(implicit ex: ExecutionContext): Future[Unit] = {
    aggregator.getData.map { result =>
      result match {
        case None => println("oopsies")
        case Some(data) => {
          println()
          println("Best tv shows")
          data.tvShows.foreach(println(_))
          println()
          println("Best tv show episodes")
          data.episodes.foreach(println(_))
          println()
          println("Most popular actors(according to moviedb)")
          data.people.foreach(println(_))
        }
      }
    }
  }

  def main(args: Array[String]): Unit = {
    import scala.concurrent.ExecutionContext.Implicits._

    // Create Akka system for thread and streaming management
      implicit val system = ActorSystem()
      system.registerOnTermination {
        System.exit(0)
      }
      implicit val materializer = ActorMaterializer()

      val wsClient = StandaloneAhcWSClient()
      val ae = new Web(wsClient)
      val parser = new Fetcher(ae)
      val aggregator =  new Aggregator(parser)

      printTVShows(aggregator)
          .andThen { case _ => wsClient.close() }
          .andThen { case _ => system.terminate() }
      }
}