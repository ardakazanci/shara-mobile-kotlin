package co.shara.data.dao

import androidx.room.Dao
import androidx.room.Query
import co.shara.data.model.Order
import co.shara.ui.model.OrderSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao : BaseDao<Order> {

    @Query("SELECT a.order_number AS orderNumber,COUNT(b.product_id) AS totalProduct, a.created_at AS createdDate FROM `Order` a INNER JOIN Product b ON a.order_id = b.order_id")
    fun getOrders(): Flow<List<OrderSummary>>
}