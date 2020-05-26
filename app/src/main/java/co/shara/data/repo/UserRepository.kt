package co.shara.data.repo

import co.shara.data.api.UserAPI
import co.shara.data.retrofit.UserLogin
import co.shara.data.retrofit.UserRegister
import co.shara.data.retrofit.UserResponse
import co.shara.network.NetworkResult
import co.shara.network.safeApiCall
import co.shara.settings.Settings
import kotlinx.coroutines.Dispatchers

class UserRepository(
    private val userAPI: UserAPI,
    private val settings: Settings
) {

    suspend fun registerUser(userRegister: UserRegister): NetworkResult<UserResponse> {
        return safeApiCall(Dispatchers.IO) {
            val registerResponse = userAPI.registerUser(userRegister)

            // update the shared pref here
            settings.setBearerToken(registerResponse?.data?.token.toString())

            registerResponse!!.copy()
        }
    }

    suspend fun loginUser(userLogin: UserLogin): NetworkResult<UserResponse> {
        return safeApiCall(Dispatchers.IO) {
            val loginResponse = userAPI.loginUser(userLogin)

            // update the shared pref here
            settings.setBearerToken(loginResponse?.data?.token.toString())

            loginResponse!!.copy()
        }
    }
}