package domain

import scala.util.Random
import java.time.LocalDateTime
import java.util.UUID


case class OrderRequest(
                           tableId: String,
                           name: String,
                       )

case class Order(
                    id: UUID = UUID.randomUUID,
                    tableId: String,
                    name: String,
                    orderedAt: String = LocalDateTime.now().toString,
                    minutesToReady: Int = Random.between(5, 15),
                )
