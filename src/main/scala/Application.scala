import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

/**
 *
 * @author Solverit
 */
object Application extends App {

  implicit val system: ActorSystem = ActorSystem("server-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

  val settings = Settings(system)

  val routes = new Routes()

  val logger = Logging(system, getClass)

	val hostAddress = settings.Http.Address
	val port = settings.Http.Port

	val bindingFuture = Http().bindAndHandle(routes(), hostAddress, port)

	bindingFuture.onComplete {
	  case Success(_) =>
		logger.info("Server online at http://{}:{}/", hostAddress, port)
	  case Failure(e) =>
		logger.error(e, "Failed to open akka-http on http://{}:{}/", hostAddress, port)
		system.terminate()
	}

}
