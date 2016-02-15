package scatchpad

import scala.annotation.tailrec

trait Parser {
  def parseText(text: String): Seq[Sentence] = {
    val tokens = splitText(text, TokenPatterns.items)
    Seq.empty
  }

  def peelOffToken[T <: Token](text: String, patterns: List[TokenPattern[T]]): (String, Option[Token]) = {
    (text.isEmpty, patterns.isEmpty) match {
      case (true, _) => ("", None)
      case (_, true) => (text, None)
      case (false, false) => {
        val matchingResults = patterns.map {
          pattern =>
            val regex = pattern.regex
            text match {
              case regex(remainingText, matchedText) => (remainingText, Some(pattern.token(matchedText)))
              case _ => ("", None)
            }
        }
        val firstMatch = matchingResults.filter(_._2.isDefined).reverse.headOption
        firstMatch match {
          case Some((remainingText, token)) => (remainingText, token)
          case _ => ("", None)
        }
      }
    }
  }

  def splitText(text: String, patterns: List[TokenPattern[Token]]): Seq[Token] = splitText(text, patterns, Seq.empty)

  @tailrec
  private def splitText(text: String, patterns: List[TokenPattern[Token]], accumulatedResult: Seq[Token]): Seq[Token] = {
    val (remainingText, token) = peelOffToken(text, patterns)
    println("=============================")
    println(s"text.size - $token")
    println("=============================")
    val newAccumulatedResult = token.fold(accumulatedResult)(_ +: accumulatedResult)
    if (remainingText.isEmpty)
      newAccumulatedResult
    else
      splitText(remainingText, patterns, newAccumulatedResult)
  }

}
