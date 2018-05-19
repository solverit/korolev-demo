package ui.component.tab

/**
 *
 * @author Solverit
 */
case class TabState(clazz: String = "", content: TabPane, tabs: Seq[TabPane]) {
  def setActive(tab: TabPane): TabState = {
    this.copy(content = tab)
  }
  def isActive(tab: TabPane): Boolean = content == tab
}

trait TabPane {
  def title: String
  def content: String
}
