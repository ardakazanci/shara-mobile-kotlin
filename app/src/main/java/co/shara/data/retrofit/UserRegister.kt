package co.shara.data.retrofit

data class UserRegister(
    val first_name: String?,
    val last_name: String?,
    val phone_number: String?,
    val email: String?,
    var password: String
)