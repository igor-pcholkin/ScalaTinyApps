import java.util.Random

object Rnd extends App {
  
  def rndPause = {
    val r = new Random()
    r.nextInt(3) + 1
  }
  
  (1 to 10).foreach(i => { println(rndPause) } )
}