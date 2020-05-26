package co.shara.data.dao

import androidx.room.Dao
import androidx.room.Query
import co.shara.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao : BaseDao<Product> {

    @Query("SELECT * FROM `Product` WHERE order_id =:orderId")
    fun getOrderProducts(orderId: Int): Flow<List<Product>>
}