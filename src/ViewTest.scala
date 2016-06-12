

object ViewTest extends App {
  val s = (1 to 50000000).view.filter(_ % 2 == 0).map(_ + 1).length
  println(s)
}