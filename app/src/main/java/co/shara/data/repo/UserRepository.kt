package co.shara.data.repo

import co.shara.data.api.UserAPI
import co.shara.data.dao.UserDao
import co.shara.data.model.User
import co.shara.data.retrofit.UserLogin
import co.shara.data.retrofit.UserRegister
import co.shara.network.NetworkResult
import co.shara.network.safeApiCall
import co.shara.settings.Settings
import kotlinx.coroutines.Dispatchers

class UserRepository(
    private val userDao: UserDao,
    private val userAPI: UserAPI,
    private val settings: Settings
) {

    suspend fun registerUser(userRegister: UserRegister): NetworkResult<User> {
        return safeApiCall(Dispatchers.IO) {
            val loginResponse = userAPI.registerUser(userRegister)
            val user = User(
                0,
                loginResponse?.user_id,
                loginResponse?.first_name,
                loginResponse?.last_name,
                loginResponse?.phone_number,
                loginResponse?.email
            )

            // update the shared pref here
            settings.setUserId(loginResponse?.user_id.toString())
//            settings.setBearerToken(loginResponse?.jwt_token.orEmpty())

            user.copy()
        }
    }

    suspend fun loginUser(userLogin: UserLogin): NetworkResult<User> {
        return safeApiCall(Dispatchers.IO) {
            val loginResponse = userAPI.loginUser(userLogin)
            val user = User(
                0,
                loginResponse?.user_id,
                loginResponse?.first_name,
                loginResponse?.last_name,
                loginResponse?.phone_number,
                loginResponse?.email
            )

            // update the shared pref here
            settings.setUserId(loginResponse?.user_id.toString())
//            settings.setBearerToken(loginResponse?.jwt_token.orEmpty())

            user.copy()
        }
    }

    suspend fun saveUser(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserById(userId: String): User {
        return userDao.getUserById(userId)
    }
}