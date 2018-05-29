package ui.main.content

import state.UiState.globalContext._
import state.UiState.globalContext.symbolDsl._
import state.{ToDoState, Todo, UserState}

import scala.concurrent.ExecutionContext.Implicits.global

/**
 *
 * @author Andrey_Mikheev
 */
object ToDoPanel {

  val inputId = elementId()
  val editInputId = elementId()

  def render(todos: ToDoState): Node =
    'div (
      "Super TODO tracker",
      'div (
        'height @= 250,
        'overflowY @= "scroll",
        (todos.list zipWithIndex) map {
          case (todo, i) =>
            'div (
              'input ('type /= "checkbox",
                if (todos.edit.nonEmpty) 'disabled /= "" else void,
                if (todo.done) 'checked /= "" else void,
                // Generate transition when clicking checkboxes
                event('click) { access =>
                  access.transition {
                    case state: UserState =>
                      val updated = state.todo.list.updated(i, state.todo.list(i).copy(done = !todo.done))
                      state.copy(todo = state.todo.copy(list = updated))

                    case state => state
                  }
                }
              ),
              if (todos.edit.contains(i)) {
                'form (
                  'marginBottom @= -10,
                  'display @= "inline-block",
                  'input (editInputId, 'display @= "inline-block", 'type /= "text", 'value := todo.text),
                  'button ('display @= "inline-block", "Save"),
                  event('submit) { access =>
                    access.property(editInputId, 'value) flatMap { value =>
                      access.transition {
                        case state: UserState =>
                          val updatedTodo  = todos.list(i).copy(text = value)
                          val updatedTodos = state.todo.list.updated(i, updatedTodo)
                          state.copy(todo = state.todo.copy(list = updatedTodos, edit = None))

                        case state => state
                      }
                    }
                  }
                )
              } else {
                'span (
                  if (todo.done) 'textDecoration @= "line-through" else void,
                  todo.text,
                  event('dblclick) { access =>
                    access.transition {
                      case state: UserState =>
                        state.copy(todo = state.todo.copy(edit = Some(i)))

                      case state => state
                    }
                  }
                )
              }
            )
        }
      ),
      'form (
        // Generate AddTodo action when 'Add' button clicked
        event('submit) { access =>
          val prop = access.property(inputId)
          prop.get('value) flatMap { value =>
            prop.set('value, "") flatMap { _ =>
              val todo = Todo(value, done = false)
              access.transition {
                case state: UserState =>
                  state.copy(todo = state.todo.copy(list = state.todo.list :+ todo))

                case state => state
              }
            }
          }
        },
        'input (
          if (todos.edit.nonEmpty) 'disabled /= "" else void,
          inputId,
          'type /= "text",
          'placeholder /= "What should be done?"
        ),
        'button (
          if (todos.edit.nonEmpty) 'disabled /= "" else void,
          "Add todo"
        )
      )
    )

}
