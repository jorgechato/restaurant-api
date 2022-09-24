package infrastructure

import cats.effect.IO
import io.circe.literal._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._


object HealthCheck {
    def healthCheckRoutes: HttpRoutes[IO] = {
        val dsl = new Http4sDsl[IO] {}
        import dsl._

        HttpRoutes.of[IO] {
            case GET -> Root / "health-check" =>
                Ok(json"""{"healthy": ${true}}""")
            case HEAD -> Root / "health-check" =>
                Ok()
        }
    }
}
