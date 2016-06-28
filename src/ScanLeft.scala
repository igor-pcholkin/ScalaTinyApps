import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import scala.util.Failure
import scala.concurrent.Await
import scala.concurrent.duration._

object ScanLeft extends App {
  def scanLeft(s: Seq[Int]) = {
    s.foldLeft((0, Seq[Int]()))((t, e) => {
      val (acc, s) = t
      val ns = acc + e
      (ns, s ++ Seq(ns))
    })._2
  }
  
  println(1 to 10)
  println(scanLeft(1 to 10))
  println(scanLeft(1 to 5))
  println(scanLeft(6 to 10))
  val f1 = Future { scanLeft(1 to 5) }
  val f2 = Future { scanLeft(6 to 10) }
  val ps = for {
    l1 <- f1
    l2 <- f2
  } yield (l1 ++ l2.map (_ + l1.last))
  val s = Await.result(ps, 5000 millis)
  println(s)
}