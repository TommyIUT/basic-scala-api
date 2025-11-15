package api.storage

import cats.effect._
import cats.effect.kernel.Ref

import api.model._

class InMemoryStore private (
    val users: Ref[IO, List[User]],
    val films: Ref[IO, List[Film]],
    val ratings: Ref[IO, List[Rating]]
) {

  // -------- USERS --------
  def addUser(user: User): IO[Unit] =
    users.update(list => user :: list)

  def getUsers(): IO[List[User]] =
    users.get

  // -------- FILMS --------
  def addFilm(film: Film): IO[Unit] =
    films.update(list => film :: list)

  def getFilms(): IO[List[Film]] =
    films.get

  // -------- RATINGS --------
  def addRating(rating: Rating): IO[Unit] =
    ratings.update(list => rating :: list)

  def getRatings(): IO[List[Rating]] =
    ratings.get
}

object InMemoryStore {

  def make: IO[InMemoryStore] =
    for {
      usersRef   <- Ref.of[IO, List[User]](List.empty)
      filmsRef   <- Ref.of[IO, List[Film]](List.empty)
      ratingRef  <- Ref.of[IO, List[Rating]](List.empty)
    } yield new InMemoryStore(usersRef, filmsRef, ratingRef)
}
