package scatchpad

import scala.annotation.tailrec

trait Parser {
  def parseText(text: String): Seq[TextElement] = {
    val textElements = TextElementPatterns.patterns.foldLeft(Seq[TextElement](NotParsedText(text))) {
      (result, pattern) =>
        result.flatMap(x => splitText(x, pattern))
    }
    textElements
  }

  def buildRegextFromPattern(tokenPattern: String) = s"(?s)(?i)(.*)($tokenPattern)(.*)".r

  def splitText(textElement: TextElement, elementPattern: TextElementPattern): Seq[TextElement] = {
    textElement match {
      case ParsedText(p) => Seq(ParsedText(p))
      case NotParsedText(t) => inner(t, elementPattern, Seq.empty)
    }
  }

  @tailrec
  final private def inner(text: String, elementPattern: TextElementPattern, acc: Seq[TextElement]): Seq[TextElement] = {
    val regex = buildRegextFromPattern(elementPattern.pattern)
    text match {
      case regex(headText, matchedText, tailText) => {
        val parsedText = elementPattern match {
          case x: SpecialWord => ParsedText(Word(matchedText))
          case x: Punctuation => ParsedText(Separator(matchedText))
          case x: SentenceSeparator => ParsedText(EndOfSentence(matchedText))
        }
        val addedElements =
          if (tailText.nonEmpty)
            Seq(parsedText, NotParsedText(tailText))

          else
            Seq(parsedText)
        inner(headText, elementPattern, addedElements ++ acc)
      }
      case _ => NotParsedText(text) +: acc
    }
  }
}
