package co.shara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val order_id: Int?,
    val user_id: Int,
    val order_number: String,
    val created_at: String,
    val updated_at: String
)