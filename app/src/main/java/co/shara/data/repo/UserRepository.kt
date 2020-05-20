package co.shara.data.repo

import co.shara.data.api.UserAPI
import co.shara.data.dao.UserDao
import co.shara.data.model.User
import co.shara.settings.Settings

class UserRepository(
    private val userDao: UserDao,
    private val userAPI: UserAPI,
    private val settings: Settings
) {

    suspend fun saveUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserById(userId: String): User {
        return userDao.getUserById(userId)
    }
}