package ui.main

import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._
import state.{AnonymousState, UserState}
import ui.login.LoginPanel

/**
 *
 * @author Solverit
 */
object Page {

  val render: Render = {
    case state: UserState      => MainPanel.render(state)
    case state: AnonymousState => LoginPanel.render(state)
  }

  val head = Seq(
    'meta('charset /= "utf-8"),
    'meta('httpEquiv /= "X-UA-Compatible", 'content /= "IE=edge"),
    'meta('name /= "viewport", 'content /= "width=device-width, initial-scale=1"),

    'title("Korolev demo project"),

    'link ('rel /= "stylesheet", 'href /= "/css/bulma.css"),
    'link ('rel /= "stylesheet", 'href /= "/css/main.css"),
    'link ('rel /= "stylesheet", 'href /= "/css/login.css"),
    'link ('rel /= "stylesheet", 'href /= "/css/font-awesome.min.css")
  )

}
