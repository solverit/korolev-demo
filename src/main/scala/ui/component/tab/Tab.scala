package ui.component.tab

import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._

/**
 *
 * @author Solverit
 */
trait Tab {

  def render(state: TabState): Node =
    'div ('class /= "tab-content",
      'div ('class /= "tabs is-boxed",
        'ul (tabHeader(state))
      ),
      tabContent(state)
    )

  def tabHeader(state: TabState): Node =
    state.tabs.map { tab =>
      val clazz = if (state.isActive(tab)) "is-active" else ""
      'li ('class /= clazz,
        'a ('id /= "", tab.title, event('click)(tabClick(tab)))
      )
    }

  def tabContent(state: TabState): Node =
    'div (
      'class /= "mpanel",
      state.content.content
    )

  def tabClick(tab: TabPane): Access => EventResult
}
