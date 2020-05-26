package co.shara.data.retrofit

data class OrderResponse(
    var order_id: Int,
    var user_id: Int,
    var order_number: String,
    var order_products: List<ProductsResponse>
)
