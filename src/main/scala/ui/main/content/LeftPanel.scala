package ui.main.content

import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._
import state.UserState
import ui.component.panel.Panel
import ui.component.tab.{Tab, TabPane}

/**
 *
 * @author Solverit
 */
object LeftPanel extends Panel {

  def render(clazz: String, state: UserState): Node =
    renderPanel(clazz, state.leftPanel, LeftTab.render(state.leftPanel.tabs))

}

object LeftTab extends Tab {

  def tabClick(tab: TabPane): Access => EventResult = { access =>
    access.transition {
      case state: UserState =>
        val tabss = state.leftPanel.tabs.setActive(tab)
        val panel = state.leftPanel.copy(tabs = tabss)
        state.copy(leftPanel = panel)

      case other => other
    }
  }
}
