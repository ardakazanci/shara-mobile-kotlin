package co.shara.data

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.shara.data.dao.UserDao
import co.shara.data.model.User

@androidx.room.Database(
    entities = [
        User::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {

    abstract fun userDao(): UserDao
}
