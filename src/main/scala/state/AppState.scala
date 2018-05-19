package state

import ui.component.panel.PanelState
import ui.component.tab.{TabPane, TabState}
import korolev.Context
import korolev.execution._
import korolev.state.DeviceId
import korolev.state.javaSerialization._

import scala.concurrent.Future

/**
  *
  * @author Solverit
  */
sealed trait UiState {
  def deviceId: DeviceId
}

object UiState {
  val globalContext: Context[Future, UiState, Any] = Context[Future, UiState, Any]
}

trait AuthorizedState extends UiState {
  def userProfile: UserProfileData
}

trait NotAuthorizedState extends UiState {

}

case class AnonymousState(deviceId: DeviceId) extends NotAuthorizedState

case class UserState(
  deviceId: DeviceId,
  leftPanel: LeftPanelState,
  rightPanel: RightPanelState
) extends AuthorizedState {
  override def userProfile: UserProfileData = UserProfileData("1", "Root")
}

case class UserProfileData(id: String, name: String)

case class LeftPanelState(clazz: String, title: String, tabs: TabState) extends PanelState
case class RightPanelState(clazz: String, title: String, tabs: TabState) extends PanelState

case class Tab(title: String, content: String) extends TabPane


// Demo data
object ContentPanelState {
  val lpTabsSeq = Seq(
    Tab("Tab1", "We are the Tab1"),
    Tab("Tab2", "We are the Tab2")
  )
  val lpTabs = TabState("", lpTabsSeq.head, lpTabsSeq)
  val leftPanel = LeftPanelState("column is-3", "Left Title", lpTabs)

  val rpTabsSeq = Seq(
    Tab("Tab3", "We are the Tab3"),
    Tab("Tab4", "We are the Tab4"),
    Tab("Tab5", "We are the Tab5"),
    Tab("Tab6", "We are the Tab6")
  )
  val rpTabs = TabState("", rpTabsSeq.head, rpTabsSeq)
  val rightPanel = RightPanelState("column is-9", "Right Title", rpTabs)
}
