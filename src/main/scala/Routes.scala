import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import controllers.MiddlewareController
import models.{ErrorMessage, JsonSupport}
import route.{KorolevRoutes, MiddlewareRoutes}

import scala.concurrent.ExecutionContext.Implicits.global

class Routes(implicit val system: ActorSystem, implicit val materializer: ActorMaterializer) extends JsonSupport {

  val controller: MiddlewareController = new MiddlewareController()
  val middlewareRoutes: MiddlewareRoutes = new MiddlewareRoutes(controller)
  val korolevRoute = new KorolevRoutes

  val exceptionHandler = ExceptionHandler {
    case t: Throwable =>
      extractUri { uri =>
        complete(ErrorMessage(StatusCodes.InternalServerError.intValue, t.getMessage))
      }
  }

  val jsonRejectionHandler = RejectionHandler
    .newBuilder()
    .handleAll[Rejection] { rejections => (context: RequestContext) =>
      {
        val route: StandardRoute = reject(rejections: _*)
        route.apply(context).map { res: RouteResult =>
          res
        }
      }
    }
    .handleNotFound {
      complete(ErrorMessage(StatusCodes.NotFound.intValue, s"Request not found"))
    }
    .result()

  def apply(): Route =
    handleRejections(jsonRejectionHandler) {
      handleExceptions(exceptionHandler) {
        concat(
          pathPrefix("api") {
            middlewareRoutes()
          },
          korolevRoute()
        )
      }
    }
}
