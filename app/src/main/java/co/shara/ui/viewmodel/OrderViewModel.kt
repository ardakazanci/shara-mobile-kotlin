package co.shara.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.shara.data.model.User
import co.shara.data.repo.OrderRepository
import co.shara.data.retrofit.UserLogin
import co.shara.data.retrofit.UserRegister
import co.shara.network.NetworkResult

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    suspend fun registerUser(userRegister: UserRegister): NetworkResult<User> {
        return orderRepository.registerUser(userRegister)
    }

    suspend fun loginUser(userLogin: UserLogin): NetworkResult<User> {
        return orderRepository.loginUser(userLogin)
    }

    suspend fun saveUser(user: User) {
        return orderRepository.saveUser(user)
    }

    suspend fun getUserById(userId: String): User {
        return orderRepository.getUserById(userId)
    }
}