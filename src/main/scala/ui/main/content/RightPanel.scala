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
object RightPanel extends Panel {

  def render(clazz: String, state: UserState): Node =
    renderPanel(clazz, state.rightPanel, RightTab.render(state.rightPanel.tabs))

}

object RightTab extends Tab {

  def tabClick(tab: TabPane): Access => EventResult = { access =>
    access.transition {
      case state: UserState =>
        val tabss = state.rightPanel.tabs.setActive(tab)
        val panel = state.rightPanel.copy(tabs = tabss)
        state.copy(rightPanel = panel)

      case other => other
    }
  }
}
