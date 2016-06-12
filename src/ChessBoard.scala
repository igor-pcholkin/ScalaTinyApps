import scala.swing._
import java.awt.Color
import javax.swing.JTable

object ChessUI extends MainFrame {
  title = "Chess"
  preferredSize = new Dimension(320, 320 + 22)
  contents = table
  
  lazy val table = createTable
  visible = true
  
  def createTable = {
    val table = new Table(8, 8) {
      override def rendererComponent(sel: Boolean, foc: Boolean, row: Int, col: Int) = {
        val c = super.rendererComponent(sel, foc, row, col)
        c.background = if ((row + col) % 2 == 0) Color.BLACK else Color.WHITE
        c
      }
    }
    table.rowHeight = 40
    table
  }
  
}

object ChessBoardApp extends App {
  ChessUI
}