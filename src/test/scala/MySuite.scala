import cats.effect.IO
import api.storage.InMemoryStore
import api.model._
import java.util.UUID
import java.time.LocalDate
import api.Seeder

class MySuite extends munit.FunSuite {

  test("InMemoryStore - addUser and getUsers") {
    val test = for {
      store <- InMemoryStore.make
      user = User(UUID.randomUUID(), "alice", Some("Cinéphile"))
      _ <- store.addUser(user)
      users <- store.getUsers()
    } yield {
      assertEquals(users.length, 1)
      assertEquals(users.head.pseudo, "alice")
    }
    test
  }

  test("InMemoryStore - addFilm and getFilms") {
    val test = for {
      store <- InMemoryStore.make
      film = Film(UUID.randomUUID(), "Inception", LocalDate.parse("2010-07-16"), "Nolan")
      _ <- store.addFilm(film)
      films <- store.getFilms()
    } yield {
      assertEquals(films.length, 1)
      assertEquals(films.head.title, "Inception")
    }
    test
  }

  test("InMemoryStore - addRating and getRatings") {
    val test = for {
      store <- InMemoryStore.make
      userId = UUID.randomUUID()
      filmId = UUID.randomUUID()
      rating = Rating(UUID.randomUUID(), userId, filmId, 9, Some("Excellent"))
      _ <- store.addRating(rating)
      ratings <- store.getRatings()
    } yield {
      assertEquals(ratings.length, 1)
      assertEquals(ratings.head.score, 9)
    }
    test
  }

  test("Seeder - populates store with data") {
    val test = for {
      store <- InMemoryStore.make
      _ <- Seeder.seed(store)
      users <- store.getUsers()
      films <- store.getFilms()
      ratings <- store.getRatings()
    } yield {
      assertEquals(users.length, 1)
      assertEquals(films.length, 2)
      assertEquals(ratings.length, 2)
    }
    test
  }
}