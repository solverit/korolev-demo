package state

import korolev.state.DeviceId

import scala.collection.mutable
import scala.concurrent.Future

/**
  *
  * @author Solverit
  */
object StateService {

  private val sessions: mutable.HashMap[DeviceId, UiState] = mutable.HashMap()

  def registerDevice(deviceId: DeviceId): UiState = {
    val initialState = UserState(
      deviceId,
      ContentPanelState.leftPanel,
      ContentPanelState.rightPanel,
      ToDoState()
    )
    sessions.update(deviceId, initialState)
    initialState
  }

  def unRegisterDevice(deviceId: DeviceId): UiState = {
    sessions.remove(deviceId)
    defaultUserState(deviceId)
  }

  def forDevice(deviceId: DeviceId): Future[UiState] = {
    val session = sessions.getOrElse(deviceId, defaultUserState(deviceId))
    Future.successful(session)
  }

  def updateForDevice(deviceId: DeviceId, state: UiState): UiState = {
    sessions.update(deviceId, state)
    state
  }

  def defaultUserState(deviceId: DeviceId): UiState =
    AnonymousState(deviceId)
}
