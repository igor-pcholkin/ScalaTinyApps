import annotation.tailrec

/**
 * Convert index to Excel column name and vice-versa
 */
object Excel extends App {
  def toChar(i: Int) = ('A'+ i).toChar
  
  @tailrec
  def index2ColName(acc: String, i: Int): String = 
    if (i <= 0) 
      acc 
    else {
      val ci = i - 1
      index2ColName(toChar(ci % 26) + acc, ci / 26)
    }
  
  def index2ColName(i: Int): String = index2ColName("", i)
  
  def colName2Index(colName: String) = 
    colName.foldLeft(0) { (acc, c) => acc * 26 + (c - 'A' + 1) } 
  
  println(index2ColName(5333))
  println(colName2Index("GWC"))
}