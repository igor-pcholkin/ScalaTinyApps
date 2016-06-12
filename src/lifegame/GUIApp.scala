package lifegame

import scala.swing._
import java.awt.Color
import javax.swing.table.AbstractTableModel
import javax.swing.SwingUtilities
import Configurations.Configuration

object GUIApp extends App {
  new LifeGame with UI {
    override def update(iteration: Long, configuration: Configuration) = {
      GUIFrame.update(iteration, configuration)
    }
  }
}

object GUIFrame extends MainFrame with UI {
  title = "Life"
  preferredSize = new Dimension(720, 720)
  contents = table

  lazy val table = createTable
  visible = true

  lazy val ROWS = 60
  lazy val COLS = 80

  def createTable = {
    val table = new Table(ROWS, COLS) {
      
      override def rendererComponent(sel: Boolean, foc: Boolean, row: Int, col: Int) = {
        val cell = super.rendererComponent(sel, foc, row, col)
        val configuration = model.asInstanceOf[GameTableModel].configuration
        cell.background = if (configuration.contains(col, row)) Color.BLACK else Color.WHITE
        cell
      }

    }
    updateUI {
      table.showGrid = true
      table.gridColor = Color.BLUE
      table.rowHeight = (preferredSize.height) / ROWS
      table.model = new GameTableModel(Set.empty)
    }
    table
  }

  class GameTableModel(val configuration: Configuration) extends AbstractTableModel {
    def getColumnCount(): Int = COLS
    def getRowCount(): Int = ROWS
    def getValueAt(x: Int, y: Int) = ""
  }

  def ensureOnboard(cell: (Int, Int)) =
    (if (cell._1 >=0 && cell._1 < COLS) cell._1 else cell._1 % COLS,
      if (cell._2 >=0 && cell._2 < ROWS) cell._2 else cell._2 % ROWS)

  override def update(iteration: Long, configuration: Configuration) = {
    updateUI {
      table.model = new GameTableModel(configuration) // map ensureOnboard)
    }
    
    Thread.sleep(if (iteration == 0) 2000 else 500)
  }

  def updateUI(task: => Unit) = SwingUtilities.invokeLater(new Runnable {
    def run() { task }
  })

}