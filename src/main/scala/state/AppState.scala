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

trait NotAuthorizedState extends UiState {}

case class AnonymousState(deviceId: DeviceId) extends NotAuthorizedState

case class UserState(
  deviceId: DeviceId,
  leftPanel: LeftPanelState,
  rightPanel: RightPanelState,
  todo: ToDoState
) extends AuthorizedState {
  override def userProfile: UserProfileData = UserProfileData("1", "Root")
}

case class Todo(text: String, done: Boolean)

case class ToDoState(
  list: Vector[Todo] = (0 to 3).toVector.map(i => Todo(s"This is TODO #$i", done = false)),
  edit: Option[Int] = None
)

case class UserProfileData(id: String, name: String)

case class LeftPanelState(clazz: String, title: String, tabs: TabState) extends PanelState
case class RightPanelState(clazz: String, title: String, tabs: TabState) extends PanelState

case class TabPaneState(title: String, content: String) extends TabPane
case class TabPaneStateTodo(title: String, content: String) extends TabPane


// Demo data
object ContentPanelState {
  val lpTabsSeq = Seq(
    TabPaneState("Tab1", "We are the Tab1"),
    TabPaneStateTodo("Tab2", "We are the Tab2")
  )
  val lpTabs    = TabState("", lpTabsSeq.head, lpTabsSeq)
  val leftPanel = LeftPanelState("column is-3", "Left Title", lpTabs)

  val rpTabsSeq = Seq(
    TabPaneState("Tab3", "We are the Tab3"),
    TabPaneState("Tab4", "We are the Tab4"),
    TabPaneState("Tab5", "We are the Tab5"),
    TabPaneState("Tab6", "We are the Tab6")
  )
  val rpTabs     = TabState("", rpTabsSeq.head, rpTabsSeq)
  val rightPanel = RightPanelState("column is-9", "Right Title", rpTabs)
}
