package ui.component.panel

import state.UiState.globalContext.symbolDsl._

/**
 *
 * @author Solverit
 */
trait Panel {

  def renderPanel(clazz: String, state: PanelState, content: Node): Node =
    'nav (
      'class /= "panel " + clazz,
      'p ('class /= "panel-heading", state.title),
      content
    )

}
