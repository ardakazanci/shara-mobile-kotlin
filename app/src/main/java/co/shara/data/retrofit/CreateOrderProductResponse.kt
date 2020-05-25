package co.shara.data.retrofit

data class CreateOrderProductResponse(
    var id: Int,
    var order_id: Int,
    var name: String,
    var price: String,
    var uom: String,
    var created_at: String,
    var updated_at: String
)