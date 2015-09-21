package eventstream
package event


sealed trait EventSubscriber[A] {
  def registerHandler(handler: EventHandler[A])

}

object EventSubscriber {
  def subscriberAndCallback[A](): (A => Unit, EventSubscriber[A]) = {
    val aggregator: Subscriber[A] =
      new Subscriber()
    val callback: (A => Unit) =
      (evt: A) => aggregator.push(evt)

    (callback, aggregator)
  }
}

trait EventHandler[A] {
  def handle(in: EventObservation[A]): Unit
}

/**
 * Internal trait that provides implementation of EventStream methods in terms of a
 * mutable sequence of observers
 */
private[event] sealed trait SubscriptionImpl[A] extends EventSubscriber[A] {

  import scala.collection.mutable

  val handlers: mutable.ListBuffer[EventHandler[A]] =
    new mutable.ListBuffer()

  def handleEvent(observation: EventObservation[A]): Unit =
    handlers.foreach(_.handle(observation))

  def registerHandler(handler: EventHandler[A]): Unit =
    handlers += handler
}

private[event] final class Subscriber[A]() extends SubscriptionImpl[A] {
  def push(in: A): Unit =
    handleEvent(ObservedValue(in))
}

sealed trait EventObservation[+A] {
  def map[B](f: A => B): EventObservation[B] =
    this match {
      case ObservedValue(a)  => ObservedValue(f(a))
      case ObservedFailed(e) => ObservedFailed(e)
      case FinalEvent  => FinalEvent
    }

  def flatMap[B](f: A => EventObservation[B]): EventObservation[B] =
    this match {
      case ObservedValue(a)  => f(a)
      case ObservedFailed(e) => ObservedFailed(e)
      case FinalEvent  => FinalEvent
    }
}
final case class ObservedValue[A](in: A) extends EventObservation[A]
final case class ObservedFailed(exn: Throwable) extends EventObservation[Nothing]
final case object FinalEvent extends EventObservation[Nothing]