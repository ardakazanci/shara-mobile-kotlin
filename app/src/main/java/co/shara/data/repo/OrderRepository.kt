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
                response!!.id,
                response.user_id,
                response.order_number,
                response.created_at,
                response.updated_at
            )
            order.copy()
        }
    }

    suspend fun createOrderProduct(createOrderProduct: CreateOrderProduct): NetworkResult<Product> {
        return safeApiCall(Dispatchers.IO) {
            val response = orderAPI.createOrderProduct(createOrderProduct)
            val product = Product(
                0,
                response!!.id,
                response.order_id,
                response.name,
                response.price,
                response.uom,
                response.created_at,
                response.updated_at
            )
            product.copy()
        }
    }

    suspend fun fetchOrderProducts() {

    }

    suspend fun fetchMyOrders(): List<OrderResponse> {
        return safeApiCall(Dispatchers.IO) {
            val response = orderAPI.fetchMyOrders()

        }
    }

    suspend fun saveOrder(order: Order) {
        orderDao.insert(order)
    }

    suspend fun saveOrderProduct(product: Product) {
        productDao.insert(product)
    }

    fun getOrders(): LiveData<List<Order>> {
        return orderDao.getOrders().asLiveData()
    }

    fun getOrderProducts(orderId: Int): LiveData<List<Product>> {
        return productDao.getOrderProducts(orderId).asLiveData()
    }
}