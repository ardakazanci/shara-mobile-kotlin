package co.shara.data.api

import co.shara.data.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST("/api/v1/user/register")
    suspend fun registerUser(@Body userValidation: User): User?

    @POST("/api/v1/user/login")
    suspend fun loginUser(@Body userLogin: User): User?

}
