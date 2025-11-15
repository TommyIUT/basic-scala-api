package api

import cats.effect.IO
import cats.implicits._
import org.http4s._
import org.http4s.dsl.io._
import org.http4s.circe._
import org.http4s.implicits._
import org.http4s.headers.Location
import io.circe.generic.auto._
import api.model._
import api.storage.InMemoryStore

import java.util.UUID

class Routes(store: InMemoryStore) {

  // JSON (de)serializers
  given EntityDecoder[IO, CreateUser] = jsonOf[IO, CreateUser]
  given EntityDecoder[IO, CreateFilm] = jsonOf[IO, CreateFilm]
  given EntityDecoder[IO, CreateRating] = jsonOf[IO, CreateRating]

  // Encoders for single resources (named givens to avoid JVM/Scala3 name clashes)
  given userEncoder: EntityEncoder[IO, User] = jsonEncoderOf[IO, User]
  given filmEncoder: EntityEncoder[IO, Film] = jsonEncoderOf[IO, Film]
  given ratingEncoder: EntityEncoder[IO, Rating] = jsonEncoderOf[IO, Rating]

  // Encoders for lists (unique given names)
  given usersListEncoder: EntityEncoder[IO, List[User]] = jsonEncoderOf[IO, List[User]]
  given filmsListEncoder: EntityEncoder[IO, List[Film]] = jsonEncoderOf[IO, List[Film]]
  given ratingsListEncoder: EntityEncoder[IO, List[Rating]] = jsonEncoderOf[IO, List[Rating]]

  // ---------------- USERS ROUTES ----------------
  val userRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "users" =>
      for {
        users <- store.getUsers()
        resp  <- Ok(users)
      } yield resp

    case req @ POST -> Root / "users" =>
      for {
        body <- req.as[CreateUser]
        user = User(UUID.randomUUID(), body.pseudo, body.description)
        _    <- store.addUser(user)
        resp <- Created(user, Location(uri"/users" / user.id.toString))
      } yield resp
  }

  // ---------------- FILMS ROUTES ----------------
  val filmRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "films" =>
      for {
        films <- store.getFilms()
        resp  <- Ok(films)
      } yield resp

    case req @ POST -> Root / "films" =>
      for {
        body <- req.as[CreateFilm]
        film = Film(UUID.randomUUID(), body.title, body.releaseDate, body.director)
        _    <- store.addFilm(film)
        resp <- Created(film, Location(uri"/films" / film.id.toString))
      } yield resp
  }

  // ---------------- RATINGS ROUTES ----------------
  val ratingRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "ratings" =>
      for {
        ratings <- store.getRatings()
        resp    <- Ok(ratings)
      } yield resp

    case req @ POST -> Root / "ratings" =>
      for {
        body <- req.as[CreateRating]

        // validate score
        _ <- if (body.score < 0 || body.score > 10)
               BadRequest("Score must be between 0 and 10")
             else IO.unit

        rating = Rating(
          UUID.randomUUID(),
          body.userId,
          body.filmId,
          body.score,
          body.comment
        )

        _    <- store.addRating(rating)
        resp <- Created(rating, Location(uri"/ratings" / rating.id.toString))
      } yield resp
  }

  // ---------------- FINAL ROUTER ----------------
  val httpApp: HttpApp[IO] =
    (userRoutes <+> filmRoutes <+> ratingRoutes).orNotFound
}