package infrastructure

import application.OrderRepo
import cats.effect.IO
import domain.OrderRequest
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import io.circe.generic.auto._
import io.circe.Json
import org.http4s.circe.CirceEntityCodec.{circeEntityDecoder, circeEntityEncoder}


object Table {
    private def errorJson(message: String) =
        Json.obj(("message", Json.fromString(message)))

    def tableRoutes(repo: OrderRepo): HttpRoutes[IO] = {
        val dsl = new Http4sDsl[IO] {}
        import dsl._

        HttpRoutes.of[IO] {
            case GET -> Root / "table" / tableId =>
                Ok(repo.getOrders(tableId))

            case req @ POST -> Root / "order" =>
                req.decode[OrderRequest] { o =>
                    repo.addOrder(o) flatMap (Created(_))
                }

            case GET -> Root / "table" / tableId / "order" / UUIDVar(itemId) =>
                repo.getOrder(itemId, tableId) flatMap {
                    case None => NotFound()
                    case Some(order) => Ok(order)
                }

            case DELETE -> Root / "table" / tableId / "order" / UUIDVar(itemId) =>
                repo.deleteOrder(itemId, tableId) flatMap {
                    case Left(message) => NotFound(errorJson(message))
                    case Right(_) => NoContent()
                }
        }
    }
}
