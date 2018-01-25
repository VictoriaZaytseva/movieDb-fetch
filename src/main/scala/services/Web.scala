package services

import play.api.libs.ws._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.json._
import cats.data.OptionT

class Web(val wsClient: StandaloneWSClient) {

  import play.api.libs.ws.JsonBodyReadables._

  def call(url: String)(implicit ex: ExecutionContext): OptionT[Future, JsValue] = {
   OptionT( wsClient.url(url).get().map { response ⇒
      val statusText: String = response.statusText
      if(statusText == "OK") {
        val body = response.body[JsValue]
        Some(body)
      }
      else {
        None
      }
    })
  }
}
