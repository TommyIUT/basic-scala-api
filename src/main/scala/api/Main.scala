package api

import cats.effect._
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Server
import com.comcast.ip4s._

import api.storage.InMemoryStore

object Main extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    for {
      // Create in-memory store
      store  <- InMemoryStore.make
      _      <- Seeder.seed(store) // données de test

      // Build routes
      routes = new Routes(store).httpApp

      // Start HTTP server
      _ <- EmberServerBuilder
            .default[IO]
            .withHost(host"0.0.0.0")
            .withPort(port"8081")
            .withHttpApp(routes)
            .build
            .use(_ => IO.never) // keep the server alive
    } yield ExitCode.Success
}
