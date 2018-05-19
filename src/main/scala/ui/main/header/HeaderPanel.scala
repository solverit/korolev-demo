package ui.main.header

import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._
import state._

/**
 *
 * @author Solverit
 */
object HeaderPanel {

  def render(state: UserState): Node =
      'nav('class/="navbar is-dark",
        'div('class /= "container",
          'div('class/="navbar-brand",
            'a('class/="navbar-item", 'href/="https://bulma.io",
              'img('src/="https://bulma.io/images/bulma-logo.png", 'alt/="Bulma: a modern CSS framework based on Flexbox", 'width/="112", 'height/="28")
            ),
            'div('class/="navbar-burger burger", 'dataTarget/="navbarExampleTransparentExample",
              'span(""),
              'span(""),
              'span("")
            )
          ),

          'div('id/="main-navbar", 'class/="navbar-menu",
            'div('class/="navbar-start",
              'a('class/="navbar-item", 'href/="https://bulma.io/",
                "Home"
              ),
              'div('class/="navbar-item has-dropdown is-hoverable",
                'a('class/="navbar-link", 'href/="/documentation/overview/start/",
                  "Docs"
                ),
                'div('class/="navbar-dropdown is-boxed",
                  'a('class/="navbar-item", 'href/="/documentation/overview/start/",
                    "Overview"
                  ),
                  'a('class/="navbar-item", 'href/="https://bulma.io/documentation/modifiers/syntax/",
                    "Modifiers"
                  ),
                  'a('class/="navbar-item", 'href/="https://bulma.io/documentation/columns/basics/",
                    "Columns"
                  ),
                  'a('class/="navbar-item", 'href/="https://bulma.io/documentation/layout/container/",
                    "Layout"
                  ),
                  'a('class/="navbar-item", 'href/="https://bulma.io/documentation/form/general/",
                    "Form"
                  )
                )
              )
            ),
            'div('class/="navbar-end",
              'div('class/="navbar-item",
                'div('class/="field is-grouped",
                  'p('class/="control",
                    'button("Tweet", 'class/="button is-dark")
                  ),
                  'p('class/="control",
                    'button("LOGOUT", 'class/="button is-dark", event('click)(logoutClick))
                  )
                )
              )
            )
          )
        )
      )

  def logoutClick: Access => EventResult = { access =>
    access.transition {
      case state: AuthorizedState =>
        StateService.unRegisterDevice(state.deviceId)

      case state: NotAuthorizedState =>
        state
    }
  }
}
