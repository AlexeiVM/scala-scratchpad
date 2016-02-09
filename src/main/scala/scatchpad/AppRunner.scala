package scatchpad

import scatchpad.model.SpecialTextElements

object AppRunner extends App {
  println("Starting run ...")
  SpecialTextElements.specialWords.map(println)
  println("Completed run")
}