package api.model

import java.util.UUID
import io.circe._
import io.circe.generic.semiauto._

case class Rating(id: UUID, userId: UUID, filmId: UUID, score: Int, comment: Option[String])

case class CreateRating(userId: UUID, filmId: UUID, score: Int, comment: Option[String])

object Rating {
  implicit val ratingDecoder: Decoder[Rating] = deriveDecoder
  implicit val ratingEncoder: Encoder[Rating] = deriveEncoder

  implicit val createRatingDecoder: Decoder[CreateRating] = deriveDecoder
  implicit val createRatingEncoder: Encoder[CreateRating] = deriveEncoder
}
