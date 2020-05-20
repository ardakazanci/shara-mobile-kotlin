package co.shara.data.api

import co.shara.data.retrofit.UserLogin
import co.shara.data.retrofit.UserRegister
import co.shara.data.retrofit.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface OrderAPI {

    @POST("/api/v1/user/register")
    suspend fun registerUser(@Body userRegister: UserRegister): UserResponse?

    @POST("/api/v1/user/login")
    suspend fun loginUser(@Body userLogin: UserLogin): UserResponse?

}
