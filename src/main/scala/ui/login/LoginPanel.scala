package ui.login

import state._
import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._

/**
 *
 * @author Solverit
 */
object LoginPanel {

  def render(state: AnonymousState): Node =
    'body (
      'section ('class /= "hero is-success is-fullheight",
        'div ('class /= "hero-body",
          'div ('class /= "container has-text-centered",
            'div ('class /= "column is-4 is-offset-4",
              'h3 ("Login", 'class /= "title has-text-grey"),
              'p ("Please login to proceed.", 'class /= "subtitle has-text-grey"),
              'div ('class /= "box",
                'figure ('class /= "avatar", 'img ('src /= "/img/avatar.png")),
                'div (
                  'div ('class /= "field",
                    'div ('class /= "control",
                      'input (
                        'class /= "input is-large",
                        'type /= "email",
                        'placeholder /= "Your Email",
                        'autofocus /= ""
                      )
                    )
                  ),
                  'div ('class /= "field",
                    'div ('class /= "control",
                      'input ('class /= "input is-large", 'type /= "password", 'placeholder /= "Your Password")
                    )
                  ),
                  'div ('class /= "field", 'label ('class /= "checkbox", 'input ("Remember me", 'type /= "checkbox"))),
                  'a ("Login", 'class /= "button is-block is-info is-large", event('click)(loginClick))
                )
              ),
              'p ('class /= "has-text-grey",
                'a ("Sign Up", 'href /= "../", " · "),
                'a ("Forgot Password", 'href /= "../", " · "),
                'a ("Need Help?", 'href /= "../")
              )
            )
          )
        )
      )
    )

  def loginClick: Access => EventResult = { access =>
    access.transition {
      case state: AuthorizedState =>
        state

      case state: NotAuthorizedState =>
        StateService.registerDevice(state.deviceId)
    }
  }

}
