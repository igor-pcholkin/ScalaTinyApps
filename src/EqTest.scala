

object EqTest extends App {
  def testInteger = { 
    val a: Integer = 50
    val b : Integer= 50;
    // use of Java intern cache
    println( a eq b)
  
    val c: Integer = 200
    val d : Integer= 200;
    println( c eq d)
  
    println( a == b)
    println( c == d)
  }

  def testInt = { 
    val a: Int = 50
    val b : Int= 50;
    // does not compile
    //println( a eq b)
  
    val c: Int = 200
    // does not compile
    val d : Int = 200;
    //println( c eq d)
  
    println( a == b)
    println( c == d)
  }

  testInteger
  testInt
}