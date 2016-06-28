package numbers
import Math.pow

/**
 * Find numbers (pcholkin numbers) which could be represented both as sum of 2 squares and 2 cubes. 
 */
object SqEqCubes extends App {
  println("Generating numbers...")
  val numbers = for { 
      i <- (1 to 1000)
      j <- (1 to 1000)
  } yield ((i, j, pow(i, 2).toLong + pow(j, 2).toLong), (i, j, pow(i, 3).toLong + pow(j, 3).toLong))
  
  println("Generating mappings...")
  val squaresMappings = (for {
    (i, j, square) <- numbers.map(_._1)
  } yield square -> (i, j)).toMap
  val cubeMappings = (for {
    (i, j, cube) <- numbers.map(_._2)
  } yield cube -> (i, j)).toMap

  println("Generating sets...")
  val squares = squaresMappings.keys.toSet
  val cubes = cubeMappings.keys.toSet
  
  println("Generating nums with indexes...")
  val numsWithIndexes = squares.intersect(cubes).toIndexedSeq.sorted map { n =>
    val si = squaresMappings(n)
    val ci = cubeMappings(n)
    (n, si, ci)
  }
  
  numsWithIndexes.foreach { t =>
    val (s1, s2) = t._2
    val (c1, c2) = t._3
    println(s"${t._1}: $s1^2+$s2^2, $c1^3+$c2^3")
  }
  
  println(new java.util.Date())
}