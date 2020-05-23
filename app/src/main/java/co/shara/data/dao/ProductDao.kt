package co.shara.data.dao

import androidx.room.Dao
import androidx.room.Query
import co.shara.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao : BaseDao<Product> {

    @Query("SELECT * FROM `Product`")
    fun getOrderProducts(orderId: Int): Flow<List<Product>>
}