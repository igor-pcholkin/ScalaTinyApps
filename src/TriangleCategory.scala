import scala.collection.mutable.Stack

/*
 * This application models triangle example from category theory.
 */

class Category {
  def id[A]: A => A = a => a
  def compose[A, B, C](g: B => C, f: A => B): A => C = g compose f
}

case class Node(nodeNum: Int)
case class TriangleConfiguration(nodes: Seq[Node])

/**
 * Triangle category specifies morphisms between triangle configurations
 */
object TriangleCategory extends Category {
  type TCFunc = TriangleConfiguration => TriangleConfiguration
  def rotate: TCFunc = tc => TriangleConfiguration(Seq(tc.nodes(1), tc.nodes(2), tc.nodes(0)))
  def flip: TCFunc = tc => TriangleConfiguration(Seq(tc.nodes(0), tc.nodes(2), tc.nodes(1)))

  implicit def toCoFunc(g: TCFunc) = new ComposableFunc(g)
  
  class ComposableFunc(g: TCFunc) {
    def <*>(f: TCFunc): ComposableFunc = compose(this, f)
    
    def apply(tc: TriangleConfiguration): TriangleConfiguration = {
      composedFunctions.foldLeft(tc) { (tc, cf) =>
        cf(tc)
      }
    }

    /**
     * Some tweaks possible due our understanding of this specific category
     */
    def compose(g: ComposableFunc, f: TCFunc): ComposableFunc = {
      (composedFunctions.top, f) match {
        case (R, R) =>
          composedFunctions.pop
          composedFunctions.push(R2)
        case (R2, R) =>
          println("reduction: R2, R")
          composedFunctions.pop
        case (F, F) =>
          println("reduction: F, F")
          composedFunctions.pop
        case _ => {
          composedFunctions.push(f)
        }
      }
      this
    }

    val composedFunctions = Stack[TCFunc](id, g)
    
  }

  // TODO:
  // the question: how to override compose of the base class?
  
  val R = rotate
  val R2 = rotate compose rotate
  val F = flip

}

object Appl extends App {
  import TriangleCategory._
  
  implicit def toNode(i: Int) = Node(i)

  val tco = TriangleConfiguration(Seq(1, 2, 3))
  println(tco)
  println(rotate(tco))
  println(compose(rotate, rotate)(tco))
  println(compose(compose(rotate, rotate), rotate)(tco))
  println((R <*> R <*> R)(tco))
  println((F <*> F <*> F)(tco))
  println((R <*> R <*> F <*> F <*> R <*> F)(tco))
  println((R <*> R <*> R <*> F <*> F <*> R <*> F)(tco))
}