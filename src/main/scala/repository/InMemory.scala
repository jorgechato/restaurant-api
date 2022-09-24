package repository

import application.OrderRepo
import cats.effect.IO
import cats.implicits.toFunctorOps
import domain.{Order, OrderRequest}

import scala.collection.mutable.HashMap
import java.util.UUID


object OrderRepo{
    class OrderImpl extends OrderRepo {
        val orders: HashMap[String, Order] = HashMap[String, Order]().empty

        override def addOrder(order: OrderRequest): IO[Order] = IO {
            val o: Order = Order(name = order.name, tableId = order.tableId)
            orders.put(o.id.toString, o)
            o
        }

        override def getOrder(id: UUID, tableId: String): IO[Option[Order]] = IO {
            //        We don't really need the filter since the order id is unique but in case we use a db with 2 tables,
            //        <orders> and <tables> we need the join with this 2 parameters
            orders.get(id.toString).filter(_.tableId == tableId)
        }

        override def getOrders(tableId: String): IO[List[Order]] = IO {
            orders.values.filter(_.tableId == tableId).toList
        }

        override def deleteOrder(id: UUID, tableId: String): IO[Either[String, Unit]] =
            for {
                removedOrder <- IO(orders.remove(id.toString))
                result = removedOrder.toRight(s"Order with ${id} in ${tableId} table not found").void
            } yield result
    }
}
