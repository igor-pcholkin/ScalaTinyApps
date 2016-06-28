package numbers/**
 * 
*/  
object PchNumbers extends App {

  def digits(n: Int) = {
    n.toString.foldLeft(Seq[Int]()) { (s, c) =>
      val d = c.toInt - '0'.toInt
      s.++(Seq(d))
    }
  }
  
  val nums = (10 to 100000000) filter { n =>
    val ds = digits(n)
    val sum = ds.zipWithIndex.map { case (d, i) =>
      val p = if (i < ds.length - 1) ds(i + 1) else ds(0)
      Math.pow(d, p)
    }.sum 
    if (n % 10000000 == 0) println(n)
    n == sum
  }
  
  println(nums)
}