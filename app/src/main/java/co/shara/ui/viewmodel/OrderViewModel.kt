package co.shara.ui.viewmodel

import androidx.lifecycle.ViewModel
import co.shara.data.model.Order
import co.shara.data.model.Product
import co.shara.data.repo.OrderRepository
import co.shara.data.retrofit.CreateOrder
import co.shara.data.retrofit.CreateOrderProduct
import co.shara.data.retrofit.OrderResponse
import co.shara.network.NetworkResult

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    suspend fun createOrder(createOrder: CreateOrder): NetworkResult<Order> {
        return orderRepository.createOrder(createOrder)
    }

    suspend fun createOrderProduct(createOrderProduct: CreateOrderProduct): NetworkResult<Product> {
        return orderRepository.createOrderProduct(createOrderProduct)
    }

    suspend fun fetchMyOrders(): NetworkResult<OrderResponse> {
        return orderRepository.fetchMyOrders()
    }

    suspend fun saveOrder(order: Order) {
        return orderRepository.saveOrder(order)
    }

    suspend fun saveOrderProduct(product: Product) {
        return orderRepository.saveOrderProduct(product)
    }
}