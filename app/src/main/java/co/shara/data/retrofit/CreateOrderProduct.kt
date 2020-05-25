package co.shara.data.retrofit

data class CreateOrderProduct(
    var order_id: Int,
    var name: String,
    var price: String,
    var uom: String
)