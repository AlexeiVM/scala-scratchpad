package scatchpad

import scala.annotation.tailrec

trait Parser {
  def parseText(text: String): Seq[Sentence] = {
    val textElements = TextElementPatterns.patterns.foldLeft(Seq[TextElement](NotParsedText(text))) {
      (result, pattern) =>
        result.map(x => splitText(x, pattern)).flatten
    }
    Seq.empty
  }

  def buildRegextFromPattern(tokenPattern: String) = s"(?s)(.*)($tokenPattern)(.*)".r

  def splitText(textElement: TextElement, elementPattern: TextElementPattern): Seq[TextElement] = {
    val regex = buildRegextFromPattern(elementPattern.pattern)
    textElement match {
      case p: ParsedText => Seq(p)
      case n: NotParsedText => inner(n.text, elementPattern, Seq.empty)
    }

    @tailrec
    def inner(text: String, elementPattern: TextElementPattern, acc: Seq[TextElement]): Seq[TextElement] = {
      text match {
        case regex(headText, matchedText, tailText) => {
          val parsedText = elementPattern match {
            case x: SpecialWord => ParsedText(Word(matchedText))
            case x: Punctuation => ParsedText(Separator(matchedText))
          }
          val addedElements =
            if (tailText.size > 0)
            Seq(parsedText, NotParsedText(tailText))
          else
            Seq(parsedText)
          inner(headText, elementPattern, addedElements ++ acc)
        }
        case _ => NotParsedText(text) +: acc
      }
    }
  }
}
