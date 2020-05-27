package co.shara.data.api

import co.shara.data.retrofit.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderAPI {

    @POST("/api/v1/order/create")
    suspend fun createOrder(@Body createOrder: CreateOrder): CreateOrderResponse?

    @POST("/api/v1/order/product")
    suspend fun createOrderProduct(@Body createOrderProduct: CreateOrderProduct): CreateOrderProductResponse?

    @GET("/api/v1/order/products")
    suspend fun fetchMyOrders(): List<OrderResponse>

}
