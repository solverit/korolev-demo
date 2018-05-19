package ui.main

import models.JsonSupport
import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._
import state._
import ui.main.content.ContentPanel
import ui.main.header.HeaderPanel

/**
 *
 * @author Solverit
 */
object MainPanel extends JsonSupport {

  val body: ElementId = elementId()

  def render(state: UserState): Node =
    'body (
      body,
      'section (
        'class /= "is-fullheight",
        HeaderPanel.render(state),
        ContentPanel.render(state)
      )
    )

}
