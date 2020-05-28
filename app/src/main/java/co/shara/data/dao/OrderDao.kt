package co.shara.data.dao

import androidx.room.Dao
import androidx.room.Query
import co.shara.data.model.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao : BaseDao<Order> {

    @Query("SELECT * FROM `Order`")
    fun getOrders(): Flow<List<Order>>
}