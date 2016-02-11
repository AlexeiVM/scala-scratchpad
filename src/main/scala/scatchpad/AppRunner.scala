package scatchpad

object TestData {
  val multilineText =
    """
This is a test multi-line text with multiple use cases. E.g. can a sentence
end with question mark? Or may be somethiong like that - GOOOOOOOOOOOOOOOOOOOOOOOOOOAL!!!!!!!!!!

this is is how people end their sentences on blackberries

Just skip a line. Special words can have sentence delimiters in them and break actoss the line, e.
 g.. Do I want to treat it as a single word? Another example is here - pre-
 clearance is a word that spns across two lines.

    """.stripMargin
}

object AppRunner extends App with Parser {
  println("Starting run ...")

//  val regex = "(?s)(?i)(.*)(e[.]\\s{0,3}g[.])(.*)".r
//  "ab\n\nc E. g. xyz" match {
//    case regex(a, b, c) => println(s"${b}")
//    case word @ _ => println(s"no match - $word")
//  }

  import TestData._
  val splitText: Seq[TextElement] = splitText(NotParsedText(multilineText), TextElementPatterns.patterns.head)
  println(s"${splitText.size}")
  splitText.map {
    text =>
      println("======================================")
      println(text)
      println("======================================")
  }
  println("Completed run")
}