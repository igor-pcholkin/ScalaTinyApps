
/**
 * My version.
 * Inspired by Tomohiko Sakamoto algorithm.
 */
object WeekOfDay extends App {
  def weekDay(day: Int, month: Int, year: Int) = {
    val monthShifts = Array ( 0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5 )
    (year - 1 + year / 4 - year / 100 + year / 400 + monthShifts(month - 1) + day - 1) % 7
  }
  
  val Array(day, month, year) = args(0).split("/").map(_.toInt)

  val weekDayName = Array ("Monday", "Tuesday", "Wendesday", "Thursday", "Friday", "Saturday", "Sunday" )
  
  assert (weekDayName(weekDay(1, 1, 1)) == "Monday")
  assert (weekDayName(weekDay(10, 1, 1)) == "Wendesday")
  assert (weekDayName(weekDay(15, 2, 1)) == "Thursday")
  assert (weekDayName(weekDay(21, 12, 1)) == "Friday")
  assert (weekDayName(weekDay(1, 1, 2)) == "Tuesday")
  assert (weekDayName(weekDay(22, 6, 3)) == "Sunday")
  assert (weekDayName(weekDay(12, 4, 1961)) == "Wendesday")
  assert (weekDayName(weekDay(3, 8, 1976)) == "Tuesday")
  assert (weekDayName(weekDay(24, 4, 2016)) == "Sunday")
  
}