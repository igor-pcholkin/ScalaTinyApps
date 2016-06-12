package lifegame

import annotation.tailrec
import Configurations.Configuration

/**
 * 
 * Emulation of Conway's Game. 
 * 
 * */
class LifeGame { self: UI =>
  import Configurations._

  def neigborCells(cell: (Int, Int)) =
    Set((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1)) map { case (dx, dy) => (cell._1 + dx, cell._2 + dy) }

  def neigborCount(cell: (Int, Int))(implicit configuration: Configuration) = {
    val neibors = neigborCells(cell)
    configuration.filter(cc => neibors.contains(cc)).size
  }

  def survivers(implicit configuration: Configuration) =
    configuration.filter { cell =>
      val nc = neigborCount(cell)
      nc == 2 || nc == 3
    }

  def offsprings(implicit configuration: Configuration) =
    configuration flatMap { cell =>
      val neigbors = neigborCells(cell)
      neigbors filter { neigbor =>
        neigborCount(neigbor) == 3
      }
    }

  def nextGeneration(implicit configuration: Configuration) = survivers ++ offsprings

  @tailrec
  private def run(i: Long, configuration: Configuration): Unit = {
    update(i, configuration)
    val ng = nextGeneration(configuration)
    run(i + 1, ng)
  }

  run(0, initConfiguration)
  
}

trait UI {
  def update(iteration: Long, configuration: Configuration)
}  



