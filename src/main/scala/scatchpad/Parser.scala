package scatchpad

/**
  * Created by Alex on 2/9/2016.
  */
trait Parser {
  def parseText(text: String): Seq[Sentence] = ???

  private def findElements(text: UnparsedText, textElements: Seq[String]): Seq[TextElement] = ???
}
