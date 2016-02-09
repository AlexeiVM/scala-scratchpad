package scatchpad.model

/**
  * Created by Alex on 2/8/2016.
  */
sealed trait Token

final case class Word(letters: String) extends Token
final case class Separator(chars: Seq[String]) extends Token
final case object EndOfSentence extends Token

sealed trait TextElement
final case class UnparsedText(value: String) extends TextElement
final case class ParsedText(tokens: Seq[Token]) extends TextElement

object SpecialTextElements {
  val spacers = Seq(" ", "\n", "\t")
  val punctuationSigns = Seq("-", ",", ";", ":", "(", ")")
  val specialWords = Seq("etc.", "e.g.", "i.e.", "e.\ng.", "i.\ne.", "et al", "et\nal")
}
