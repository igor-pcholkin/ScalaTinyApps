import scala.collection.mutable.Map

object Memoize extends App {
  def memoize[A, B](f: A => B) = {
    val p: Map[A, B] = Map()
    a: A =>
      p.get(a) match {
        case None =>
          val v = f(a)
          p.put(a, v)
          println("Calling f")
          v
        case Some(v) =>
          println("returning cached value")
          v
      }
  }                        
  
  lazy val m = memoize { x: Int => x * 10 }
  
  println (m(3), m(4), m(5), m(3), m(4), m(5), m(6))
}