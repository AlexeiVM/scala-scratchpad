package scatchpad

object AppRunner extends App {
  println("Starting run ...")

  val regex = "(?s)(?i)(.*)(e[.]\\s{0,3}g[.])(.*)".r
  "ab\n\nc E. g. xyz" match {
    case regex(a, b, c) => println(s"${b}")
    case word @ _ => println(s"no match - $word")
  }

  println("Completed run")
}