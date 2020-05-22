package co.shara.data.dao

import androidx.room.Dao
import co.shara.data.model.Product

@Dao
interface ProductDao : BaseDao<Product>