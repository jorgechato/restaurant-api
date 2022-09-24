package infrastructure

import application.OrderRepo
import repository.OrderRepo
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.server.Router


object Main extends IOApp {
    private val repo: OrderRepo = new OrderRepo.OrderImpl

    val httpRoutes = Router[IO](
        "" -> Table.tableRoutes(repo),
        "" -> HealthCheck.healthCheckRoutes
    ).orNotFound

    override def run(args: List[String]): IO[ExitCode] = {

        BlazeServerBuilder[IO]
            .bindHttp(8080, "0.0.0.0")
            .withHttpApp(httpRoutes)
            .serve
            .compile
            .drain
            .as(ExitCode.Success)
    }
}
