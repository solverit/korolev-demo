package ui.main.content

import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._
import state.{TabPaneState, TabPaneStateTodo, UserState}
import ui.component.panel.Panel
import ui.component.tab.{Tab, TabPane}

/**
 *
 * @author Solverit
 */
object LeftPanel extends Panel {

  def render(clazz: String, state: UserState): Node =
    renderPanel(clazz, state.leftPanel, LeftTab.render(state))

}

object LeftTab extends Tab {

  def render(state: UserState): Node =
    'div (
      'class /= "tab-content",
      'div (
        'class /= "tabs is-boxed",
        'ul (tabHeader(state.leftPanel.tabs))
      ),
      tabContent(state)
    )

  def tabContent(state: UserState): Node = {
    state.leftPanel.tabs.content match {
      case p: TabPaneState =>
        super.tabContent(state.leftPanel.tabs)

      case p: TabPaneStateTodo =>
        'div ('class /= "mpanel",
          ToDoPanel.render(state.todo)
        )
    }
  }

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
