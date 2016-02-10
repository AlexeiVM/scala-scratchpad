package scatchpad

import scala.annotation.tailrec

trait Parser {
  def parseText(text: String): Seq[Sentence] = {
    val textElements = TextElementPatterns.patterns.foldLeft(Seq[TextElement](NotParsedText(text))) {
      (result, pattern) =>
        splitText(result, pattern)
    }
    Seq.empty
  }

  def buildRegextFromPattern(tokenPattern: String) = s"(?s)(.*)($tokenPattern)(.*)".r

  @tailrec
  private def splitText(textElements: Seq[TextElement], elementPattern: TextElementPattern): Seq[TextElement] = {
    val regex = buildRegextFromPattern(elementPattern.pattern)
    textElements.map {
      case p: ParsedText => Seq(p)
      case n: NotParsedText => {
        n.text match {
          case regex(headText, matchedText, tailText) => {
            val token = elementPattern match {
              case x: SpecialWord => Word(matchedText)
              case x: Punctuation => Separator(matchedText)
            }
            val newTextElements: Seq[TextElement] = Seq(NotParsedText(headText), ParsedText(token), NotParsedText(tailText))
            splitText(newTextElements, elementPattern)
          }
          case _ => Seq(n)
        }
      }

    }.flatten
  }
}
