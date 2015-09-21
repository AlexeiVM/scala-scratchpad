package eventstream
package event

import scala.language.higherKinds

trait Functor[F[_]] {

  def map[A, B](fa: F[A])(f: A => B): F[B]

}

trait Monad[F[_]] extends Functor[F] {

  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]

  def point[A](a: A): F[A]

}

object EventStreamInstances {
  implicit object stream extends Monad[EventStream] {
    override def map[A, B](fa: EventStream[A])(f: (A) => B): EventStream[B] = fa.map(f)

    override def flatMap[A, B](fa: EventStream[A])(f: (A) => EventStream[B]): EventStream[B] = ???

    override def point[A](a: A): EventStream[A] = ???
  }
}

object ListInstances {
  implicit object list extends Monad[List] {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa.map(f)

    override def flatMap[A, B](fa: List[A])(f: (A) => List[B]): List[B] = fa.flatMap(f)

    override def point[A](a: A): List[A] = ???
  }
}
