import akka.actor._
import com.typesafe.config.Config

/**
 * Application Config.
 *
 * @author Solverit
 */
class Settings(config: Config, system: ExtendedActorSystem) extends Extension {

  object Http {
    val Address: String = config.getString("app.http.host")
    val Port: Int = config.getInt("app.http.port")
  }

}

object Settings extends ExtensionId[Settings] with ExtensionIdProvider {

  override def lookup: Settings.type = Settings

  override def createExtension(system: ExtendedActorSystem) = new Settings(system.settings.config, system)

  /**
   * Java API: retrieve the Settings extension for the given system.
   */
  override def get(system: ActorSystem): Settings = super.get(system)
}

trait ActorSettings {
  this: Actor =>
  val settings: Settings = Settings(context.system)
}
