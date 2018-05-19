package ui.main.content

import state.UiState.globalContext.symbolDsl._
import state.UserState

/**
 *
 * @author Solverit
 */
object ContentPanel {

  def render(state: UserState): Node =
    'div ('class /= "columns is-gapless mpanel",
          LeftPanel.render("column is-3 mpanel", state),
          RightPanel.render("column is-9 mpanel", state)
    )

}
