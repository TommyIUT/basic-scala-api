package api.model

import java.util.UUID
import io.circe._
import io.circe.generic.semiauto._

case class User(id: UUID, pseudo: String, description: Option[String])

case class CreateUser(pseudo: String, description: Option[String])

object User {
  implicit val userDecoder: Decoder[User] = deriveDecoder
  implicit val userEncoder: Encoder[User] = deriveEncoder

  implicit val createUserDecoder: Decoder[CreateUser] = deriveDecoder
  implicit val createUserEncoder: Encoder[CreateUser] = deriveEncoder
}
