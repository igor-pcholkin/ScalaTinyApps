

import java.util.Calendar

object KeyStamp extends App {
  def ts = Calendar.getInstance.getTimeInMillis
  
  def takeKey(prevTs: Long): Unit = {
    System.in.read()
    val cTs = ts
    println(if (prevTs == 0) "-" else cTs - prevTs)
    takeKey(cTs)
  }
  
  takeKey(0)
}