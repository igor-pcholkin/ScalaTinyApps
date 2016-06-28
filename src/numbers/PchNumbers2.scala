package numbers/**
 * 
*/  
object PchNumbers2 extends App {

  def digits(n: Int) = {
    n.toString.foldLeft(Seq[Int]()) { (s, c) =>
      val d = c.toInt - '0'.toInt
      s.++(Seq(d))
    }
  }
  
  def restPowDig(arr: Seq[Int], p: Int):Long = {
    arr.foldLeft(0) { (acc, a) => 
      acc + Math.pow(a, p).toInt
    }
  }
  
  val nums = (666666666 to 1000000000) foreach { n =>
    val ds = digits(n)
    val sums = ds.zipWithIndex.map { case (d, i) =>
      restPowDig(ds.slice(0, i) ++ ds.slice(i + 1, ds.length), d)
    }
    //if (n % 1000000 == 0) println(n)
    if (sums.forall(s => s == sums(0)))
      println(n)
  }
  
  //println(nums)
}