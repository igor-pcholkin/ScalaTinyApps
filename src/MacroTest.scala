import scala.language.experimental.macros

object MacroTest extends App {
  import MacroDef._
  
  val qq = "Privet"
  debug(qq)
}