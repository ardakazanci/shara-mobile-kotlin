package co.shara.data

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.shara.data.dao.OrderDao
import co.shara.data.dao.ProductDao
import co.shara.data.model.Order
import co.shara.data.model.Product

@androidx.room.Database(
    entities = [
        Order::class,
        Product::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun orderDao(): OrderDao
    abstract fun productDao(): ProductDao
}
