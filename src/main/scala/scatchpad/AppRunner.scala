package scatchpad

object TestData {
  val multilineText =
    """
This is a test multi-line text with multiple use cases. E.g. can a sentence
end with question mark? Or may be something like that - GOOOOOOOOOOOOOOOOOOOOOOOOOOAL!!!!!!!!!!

this is is how people end their sentences on blackberries

Just skip a line. Special words can have sentence delimiters in them and break actoss the line, e.
 g.. Do I want to treat it as a single word? Another example is here - pre-
clearance is a word that spans across two lines.
    """
}

object AppRunner extends App with Parser {
  //  println("Starting run ...")
  //    val regex = "(?s)(?i)(.*)([!]{1,100}$)".r //TokenPatterns.items(4).regex
  //  println(regex)
  //    """GOOOOOOOOOOOOOOOOOOOOOOOOOOAL!!!!!!!!!!""" match {
  //      case regex(head, token) => println(s"test match - ${token}, head - $head")
  //      case word @ _ => println(s"no match - $word")
  //    }

  val patterns = TokenPatterns.items.map(_.asInstanceOf[TokenPattern[Token]])
  val text = TestData.multilineText

  val result = parseText(text, patterns)
  result.sentences.map(println)
  println("Completed run")
}