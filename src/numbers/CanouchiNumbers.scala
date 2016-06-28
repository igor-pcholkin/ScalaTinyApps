package numbers/**
 * My rediscovery of "Canouchi" numbers which I tempted originally to call by my own name.
 * 
 * The app finds "Canouchi numbers" with the property such that
 * sum of it's digits each in corresponding power of that digit equals that particular number.
 *
 * Below < 100000000 there are only 2 such numbers: 1 and 3435.
 * 
*/  
object Canouchi extends App {

  val nums = (1 to 10000) filter { n =>
    val sum = n.toString.foldLeft(0.toLong) { (sum, c) =>
      val d = c.toLong - '0'.toLong
      (sum + Math.pow(d, d)).toLong
    }
    n == sum
  }
  
  println(nums)
}