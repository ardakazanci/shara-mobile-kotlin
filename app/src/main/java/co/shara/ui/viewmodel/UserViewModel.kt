package co.shara.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.shara.data.repo.UserRepository
import co.shara.data.retrofit.UserLogin
import co.shara.data.retrofit.UserRegister
import co.shara.data.retrofit.UserResponse
import co.shara.network.NetworkResult

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun registerUser(userRegister: UserRegister): NetworkResult<UserResponse> {
        return userRepository.registerUser(userRegister)
    }

    suspend fun loginUser(userLogin: UserLogin): NetworkResult<UserResponse> {
        return userRepository.loginUser(userLogin)
    }

}