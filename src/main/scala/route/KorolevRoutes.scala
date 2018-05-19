package route

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import korolev.akkahttp._
import korolev.execution._
import korolev.server._
import korolev.state.javaSerialization._
import state.{StateService, UiState}
import ui.main.Page

import scala.concurrent.Future

/**
  *
  * @author Solverit
  */
class KorolevRoutes(implicit val system: ActorSystem, implicit val materializer: ActorMaterializer) {

  val stateStorage = StateStorage.forDeviceId[Future, UiState] { deviceId =>
    StateService.forDevice(deviceId)
  }

  val config = KorolevServiceConfig[Future, UiState, Any](
    stateStorage = stateStorage,
    router = emptyRouter,
    head = Page.head,
    render = Page.render
  )

  def apply(): Route = akkaHttpService(config).apply(AkkaHttpServerConfig())
}
