package co.shara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val product_id: Int,
    var order_id: Int,
    var name: String,
    var price: String,
    var uom: String
)