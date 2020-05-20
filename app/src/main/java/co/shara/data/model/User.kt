package co.shara.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val user_id: String?,
    val first_name: String?,
    val last_name: String?,
    val phone_number: String?,
    val email: String?
)