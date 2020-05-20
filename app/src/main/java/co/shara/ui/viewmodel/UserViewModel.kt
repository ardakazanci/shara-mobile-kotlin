package co.shara.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.shara.data.model.User
import co.shara.data.repo.UserRepository

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    suspend fun saveUser(user: User) {
        return userRepository.saveUser(user)
    }

    suspend fun getUserById(userId: String): User {
        return userRepository.getUserById(userId)
    }
}