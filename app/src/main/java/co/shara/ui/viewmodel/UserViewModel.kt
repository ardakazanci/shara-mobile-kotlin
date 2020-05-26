package co.shara.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.shara.data.repo.UserRepository
import co.shara.data.retrofit.UserLogin
import co.shara.data.retrofit.UserRegister
import co.shara.network.NetworkResult

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun registerUser(userRegister: UserRegister): NetworkResult<User> {
        return userRepository.registerUser(userRegister)
    }

    suspend fun loginUser(userLogin: UserLogin): NetworkResult<User> {
        return userRepository.loginUser(userLogin)
    }

    suspend fun saveUser(user: User) {
        return userRepository.saveUser(user)
    }

    suspend fun getUserById(userId: String): User {
        return userRepository.getUserById(userId)
    }
}