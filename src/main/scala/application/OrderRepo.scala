package application

import cats.effect.IO
import domain.{Order, OrderRequest}

import java.util.UUID

trait OrderRepo {
    def addOrder(order: OrderRequest): IO[Order]

    def getOrder(id: UUID, tableId: String): IO[Option[Order]]

    def getOrders(tableId: String): IO[List[Order]]

    def deleteOrder(id: UUID, tableId: String): IO[Either[String, Unit]]
}
