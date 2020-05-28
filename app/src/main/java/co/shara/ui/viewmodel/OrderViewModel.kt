package co.shara.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import co.shara.data.model.Order
import co.shara.data.model.Product
import co.shara.data.repo.OrderRepository
import co.shara.data.retrofit.CreateOrder
import co.shara.data.retrofit.CreateOrderProduct
import co.shara.network.NetworkResult
import co.shara.ui.model.OrderSummary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(
    private val orderRepository: OrderRepository
) : ViewModel() {

    suspend fun createOrder(createOrder: CreateOrder): NetworkResult<Order> {
        return orderRepository.createOrder(createOrder)
    }

    suspend fun createOrderProduct(createOrderProduct: CreateOrderProduct): NetworkResult<Product> {
        return orderRepository.createOrderProduct(createOrderProduct)
    }

    fun fetchMyOrders() = GlobalScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.IO) {
            orderRepository.fetchOrderProducts()
        }
    }

    fun getOrders(): LiveData<List<OrderSummary>> {
        return orderRepository.getOrders()
    }

    fun getOrderProducts(orderId: Int): LiveData<List<Product>> {
        return orderRepository.getOrderProducts(orderId)
    }
}