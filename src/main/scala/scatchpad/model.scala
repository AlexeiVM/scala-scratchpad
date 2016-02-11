package scatchpad

/**
  * Created by Alex on 2/8/2016.
  */
sealed trait Token

final case class Word(letters: String) extends Token

final case class Separator(chars: String) extends Token

final case object EndOfSentence extends Token

sealed trait TextElement

final case class ParsedText[T <: Token](token: T) extends TextElement

final case class NotParsedText(text: String) extends TextElement

sealed trait TextElementPattern {
  val pattern: String
}

final case class SpecialWord(pattern: String) extends TextElementPattern

final case class Punctuation(pattern: String) extends TextElementPattern


object TextElementPatterns {
  val patterns = Seq[TextElementPattern](SpecialWord("etc."),
    SpecialWord("e[.]\\s*g[.]"),
    SpecialWord("i[.]\\s*e[.]"),
    SpecialWord("et\\s*al"),
    Punctuation("\\s*-\\s*"),
    Punctuation(","),
    Punctuation(":"),
    Punctuation(";"),
    Punctuation("("),
    Punctuation(")")
  )
}

final case class Sentence(elements: Seq[Token])