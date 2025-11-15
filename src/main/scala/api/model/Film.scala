package api.model

import java.util.UUID
import java.time.LocalDate
import io.circe._
import io.circe.generic.semiauto._

case class Film(id: UUID, title: String, releaseDate: LocalDate, director: String)

case class CreateFilm(title: String, releaseDate: LocalDate, director: String)

object Film {
  implicit val localDateEncoder: Encoder[LocalDate] = Encoder.encodeString.contramap[LocalDate](_.toString)
  implicit val localDateDecoder: Decoder[LocalDate] = Decoder.decodeString.emap { str =>
    try Right(LocalDate.parse(str))
    catch { case e: Exception => Left(e.getMessage) }
  }

  implicit val filmDecoder: Decoder[Film] = deriveDecoder
  implicit val filmEncoder: Encoder[Film] = deriveEncoder

  implicit val createFilmDecoder: Decoder[CreateFilm] = deriveDecoder
  implicit val createFilmEncoder: Encoder[CreateFilm] = deriveEncoder
}
