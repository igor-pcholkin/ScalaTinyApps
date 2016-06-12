package lifegame

import Configurations.Configuration

object ConsoleApp extends App {
  new LifeGame with UI {
    override def update(iteration: Long, configuration: Configuration) = {
      println(s"$iteration: $configuration")
    }
  }
}
