package scatchpad

object TestData {
  val multilineText =
    """
This is test multi-line text with multiple use cases. E.g. can a sentence
end with question mark? Or may be somethiong like that - GOOOOOOOOOOOOOOOOOOOOOOOOOOAL!!!!!!!!!!

this is is how people end their sentences on blackberries

Just skip a line. Special words can have sentence delimiters in them and break actoss the line, e.
 g.. Do I want to treat it as a single word? Another example is here - pre-
 clearence is a word that spns across two lines.

    """.stripMargin
}
object AppRunner extends App {
  println("Starting run ...")

  val regex = "(?s)(?i)(.*)(e[.]\\s{0,3}g[.])(.*)".r
  "ab\n\nc E. g. xyz" match {
    case regex(a, b, c) => println(s"${b}")
    case word @ _ => println(s"no match - $word")
  }

  println("Completed run")
}