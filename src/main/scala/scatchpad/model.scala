package scatchpad

import scala.reflect._

sealed trait Token

final case class Word(letters: String) extends Token

final case class Separator(chars: String) extends Token

final case class EndOfSentence(chars: String) extends Token

case class TokenPattern[T <: Token : ClassTag](pattern: String) {
  def regex = s"(?s)(?i)(.*)($pattern)".r

  def token(text: String): T = {
    this match {
      case _ if classTag[T] == classTag[Word] => Word(text).asInstanceOf[T]
      case _ if classTag[T] == classTag[Separator] => Separator(text).asInstanceOf[T]
      case _ if classTag[T] == classTag[EndOfSentence] => EndOfSentence(text).asInstanceOf[T]
    }
  }
}

object TokenPatterns {
  val items = List(
    TokenPattern[Word]("e[.][ |\t]*(?:\\r\\n){0,1}g[.]"),
    TokenPattern[Word]("i[.][ |\t]*(?:\\r\\n){0,1}e[.]"),
    TokenPattern[Word]("etc."),
    TokenPattern[Word]("et[ |\t]*(?:\\r\\n){0,1}al"),
    TokenPattern[Word]("\\b\\w+[-](?:[ |\t]?\\r\\n)?\\w+$"),
    TokenPattern[Separator]("\\s+-\\s+"),
    TokenPattern[Separator]("[,|:|;|\\(|\\)]"),
    TokenPattern[EndOfSentence]("[!]+"),
    TokenPattern[EndOfSentence]("[.]+|[?]+|(?:[!]+)|(?:\\r\\n)+$"),
    TokenPattern[Separator]("\\s+"),
    TokenPattern[Word]("\\b\\w+")
  )
}

final case class Sentence(elements: Seq[Token]) {
  def append(token: Token): Sentence = Sentence(this.elements :+ token)

  def isIncomplete = {
    val lastElement = this.elements.reverse.headOption
    lastElement match {
      case Some(EndOfSentence(_)) => false
      case _ => true
    }
  }
}

final case class Text(sentences: Seq[Sentence]) {
  def appendToken(token: Token) = {
    val lastSentence = sentences.reverse.headOption.getOrElse(Sentence(Seq.empty))
    val newTail = token match {
      case v: EndOfSentence => Seq(lastSentence.append(v))
      case v@_ if lastSentence.isIncomplete => Seq(lastSentence.append(v))
      case v@_ => Seq(lastSentence, Sentence(Seq(v)))
    }
    val newSentences = sentences.take(sentences.size - 1) ++ newTail
    Text(newSentences)
  }

}