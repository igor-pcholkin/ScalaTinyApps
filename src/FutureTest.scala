

object FutureTest extends App {
  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
  
  val f = Future(12)
  f map {
    v => println(v)
  }
  Thread.sleep(1000)
}