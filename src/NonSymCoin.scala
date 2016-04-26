import scala.util.Random

/**
 * Construct non-symmetric coin.
 * Turn that coin to simmetric one.
 * Inspired by task mentioned in 
 * https://www.youtube.com/watch?v=QwmmkwYg6yA
 * Here is my own solution. 
 * The idea is no matter of value p each second throw meaning of heads if changed to opposite.
 */
object NonSymCoin extends App {
  
  // non-symmetric coin
  class NSCoin(p: Double) {
    val r = new Random()
    def flip: Boolean = {
      r.nextDouble() < p
    }
  }
  
  // symmetric coin
  class SCoin(nsCoin: NSCoin) {
    var counter = 0
    def flip: Boolean = {
      val f = nsCoin.flip
      counter = 1 - counter
      if (counter == 0) f else !f
    }
  }
  
  if (args.length < 1) {
    throw new Exception("Specify probability of coin")
  }
  
  val p = args(0)
  val c = new SCoin(new NSCoin(p.toDouble))
  // make series of flips
  val nThrows = 10000
  val flips = (1 to nThrows).map(_ => c.flip)
  val hCount = flips.count { h => h }
  //println(flips)
  println(s"Number of heads: $hCount, probability of heads: ${hCount.toDouble / nThrows}")
  
}