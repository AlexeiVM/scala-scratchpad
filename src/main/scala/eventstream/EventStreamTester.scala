import eventstream._
import eventstream.event._
import eventstream.keyboard.Key.Character

object EventStreamTester extends App {
  def keyboardObserver(event: Observation[keyboard.Key]) = {
//    event match {
//      case Value(Character(c)) => println(c)
//      case Value(s @ _) => println(s)
//      case a @ _ => println(a)
//    }
  }

  def upAndDownObserver(event: Observation[Int]) = {
//    event match {
//      case Value(a) if a > 0 => println(s"Up $a")
//      case Value(a) if a < 0 => println(s"Down ${-1*a}")
//      case a @ _ => println(a)
//    }
  }
  val (callback1, stream1) = EventStream.streamAndCallback[keyboard.Key]()
  stream1.onObservation(keyboardObserver)

  val (callback2, stream2) = EventStream.streamAndCallback[Int]()
  stream2.onObservation(upAndDownObserver)

  val stream3 = stream1.join(stream2)((ts, m) => (m*2-1,ts))
//  val stream4 = stream1.map(x => List(x, x))
  stream3.map(x => {println(x); x})
//  val stream3 = EventStreamInstances.stream.map(stream2)(x => {println(s"$x"); x*2})
//  val list1 = ListInstances.list.map(List(1,2,3))(x => {println(s"$x"); x*2})
/*
We don't actually receive events we give callback function to the event source not EventStream source
 */
  callback1(keyboard.Key.Left)
  callback1(keyboard.Key.Right)
  callback1(keyboard.Key.Character('H'))
  callback2(1)
  callback1(keyboard.Key.Character('e'))
  callback1(keyboard.Key.Character('l'))
  callback1(keyboard.Key.Character('l'))
  callback1(keyboard.Key.Character('o'))
  callback1(keyboard.Key.Character(' '))
  callback1(keyboard.Key.Character('W'))
  callback2(-5)
  callback1(keyboard.Key.Character('o'))
  callback1(keyboard.Key.Character('r'))
  callback1(keyboard.Key.Character('l'))
  callback1(keyboard.Key.Character('d'))



}
