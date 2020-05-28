package co.shara.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["product_id"], unique = true)])
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val product_id: Int,
    val order_id: Int,
    val name: String,
    val price: String,
    val uom: String,
    val created_at: String,
    val updated_at: String
)