package route

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import controllers.MiddlewareController
import models.JsonSupport

class MiddlewareRoutes(controller: MiddlewareController) extends JsonSupport {

  def apply(): Route =
    path("health") {
      get {
        complete(controller.health)
      }
    } ~
    path("info") {
      get {
        complete(controller.info)
      }
    }
}
