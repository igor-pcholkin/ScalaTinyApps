
/*
 * My implementation of Kleisli arrow (h) or Kleisli category.
 * f & g have partially compatible input/return types.
 * h is a composing adapter for those.
 */
object Kleisli extends App {
  case class Person(name: String)
  
  val f: Int => List[String] = { i =>
    List(i.toString, (i + 1).toString, (i + 2).toString)
  }
  
  val g: String => List[Person] = { s =>
    List(Person(s), Person(""), Person(s + s))
  }
  
  val h: Int => List[Person] = { i => f(i).flatMap { s => g(s) } }
  
  println(h(5))
}