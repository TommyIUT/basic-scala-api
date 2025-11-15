package api

import cats.effect._
import api.storage.InMemoryStore
import api.model._
import java.util.UUID
import java.time.LocalDate

object Seeder {

  def seed(store: InMemoryStore): IO[Unit] = for {
    // Créer un user
    userId <- IO.pure(UUID.randomUUID())
    user <- IO.pure(User(userId, "tommy", Some("Fan de cinema")))
    _ <- store.addUser(user)

    // Créer des films
    filmId1 <- IO.pure(UUID.randomUUID())
    film1 <- IO.pure(Film(filmId1, "Taste of Cherry", LocalDate.parse("1997-11-26"), "Abbas Kiarostami"))
    _ <- store.addFilm(film1)

    filmId2 <- IO.pure(UUID.randomUUID())
    film2 <- IO.pure(Film(filmId2, "Love Exposure", LocalDate.parse("2009-01-31"), "Sion Sono"))
    _ <- store.addFilm(film2)

    // Créer des ratings
    rating1 <- IO.pure(Rating(UUID.randomUUID(), userId, filmId1, 10, Some("Mon film préféré")))
    _ <- store.addRating(rating1)

    rating2 <- IO.pure(Rating(UUID.randomUUID(), userId, filmId2, 10, None))
    _ <- store.addRating(rating2)
  } yield ()
}