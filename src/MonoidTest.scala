

import scala.util.{Try, Failure, Success}

/**
 * This test app shows the following features:
 * 1. Defining a monoid
 * 2. Use monoid as a type class.
 * 3. Using simple monoids as building blocks for more complex monoids.
 * 4. Adding monoidal functionality to Try type in order to support DSL like Success(2) + Success(2) 
 */
object MonoidTest extends App {

  abstract class Monoid[T] {
    def mappend(a1: T, a2: T): T
    def zero: T
  }
  
  implicit val intMonoid = new Monoid[Int] {
    def mappend(a1: Int, a2: Int) = a1 + a2
    def zero = 0
  }

  implicit val strMonoid = new Monoid[String] {
    def mappend(a1: String, a2: String) = a1 + a2
    def zero = ""
  }

  class TryMonoid[A](t: Try[A])(implicit a: Monoid[A]) extends Monoid[Try[A]] {
    override def mappend(a1: Try[A], a2: Try[A]) = {
      for {
        v1 <- a1
        v2 <- a2
      } yield a.mappend(v1, v2)
    }
    override def zero = Failure(new RuntimeException("Zero"))
    def +(t2: Try[A]) = mappend(t, t2)
  }

  implicit def toTryMonoid[A](t: Try[A])(implicit a: Monoid[A]) = new TryMonoid(t)
  
  println(Try(5) + Try(6) + Try(10))
  println(Try("Hi") + Success(" ") + Try("blabla"))
  println(Try(5) + Failure(new RuntimeException("oops")) + Try(10))
}