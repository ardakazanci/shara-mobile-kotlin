package co.shara.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import co.shara.data.api.OrderAPI
import co.shara.data.dao.OrderDao
import co.shara.data.dao.ProductDao
import co.shara.data.model.Order
import co.shara.data.model.Product
import co.shara.data.retrofit.CreateOrder
import co.shara.data.retrofit.CreateOrderProduct
import co.shara.data.retrofit.OrderResponse
import co.shara.network.NetworkResult
import co.shara.network.safeApiCall
import co.shara.settings.Settings
import kotlinx.coroutines.Dispatchers

class OrderRepository(
    private val orderDao: OrderDao,
    private val productDao: ProductDao,
    private val orderAPI: OrderAPI,
    private val settings: Settings
) {

    suspend fun createOrder(createOrder: CreateOrder): NetworkResult<Order> {
        return safeApiCall(Dispatchers.IO) {
            val response = orderAPI.createOrder(createOrder)
            val order = Order(
                0,
                response?.id?.toInt()
            )
            order.copy()
        }
    }

    suspend fun createOrderProduct(createOrderProduct: CreateOrderProduct): NetworkResult<Product> {
        return safeApiCall(Dispatchers.IO) {
            val response = orderAPI.createOrderProduct(createOrderProduct)
            val product = Product(
                0,
                response?.id?.toInt()
            )
            product.copy()
        }
    }

    suspend fun fetchMyOrders(): NetworkResult<OrderResponse> {
        return safeApiCall(Dispatchers.IO) {
            val response = orderAPI.fetchMyOrders()
            response!!.copy()
        }
    }

    suspend fun saveOrder(order: Order) {
        orderDao.insert(order)
    }

    suspend fun saveOrderProduct(product: Product) {
        productDao.insert(product)
    }

    suspend fun getOrders(): LiveData<List<Order>> {
        return orderDao.getOrders().asLiveData()
    }
}