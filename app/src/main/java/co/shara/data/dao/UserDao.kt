package co.shara.data.dao

import androidx.room.Dao
import androidx.room.Query
import co.shara.data.model.User

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM User WHERE user_id = :userId LIMIT 1")
    suspend fun getUserById(userId: String): User
}