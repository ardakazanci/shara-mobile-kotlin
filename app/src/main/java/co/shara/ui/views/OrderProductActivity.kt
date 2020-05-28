package co.shara.ui.views

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.shara.R
import co.shara.data.model.Product
import co.shara.data.retrofit.CreateOrderProduct
import co.shara.network.NetworkResult
import co.shara.ui.adapter.ProductAdapter
import co.shara.ui.viewmodel.OrderViewModel
import co.shara.util.makeSnackbar
import kotlinx.android.synthetic.main.activity_order_product.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderProductActivity : AppCompatActivity() {

    private val orderViewModel: OrderViewModel by viewModel()
    private var dialog: Dialog? = null

    private lateinit var productAdapter: ProductAdapter

    private lateinit var orderId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_product)

        orderId = intent?.getStringExtra("order_id").toString()

        if (orderId.isNullOrBlank()) {
            orderId = "0"
        }

        toolbar.title = "Order No # $orderId"

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val itemDecor = DividerItemDecoration(this, LinearLayout.VERTICAL)
        val layoutManager = LinearLayoutManager(this)
        recycler_view_order_product_list.layoutManager = layoutManager
        recycler_view_order_product_list.addItemDecoration(itemDecor)

        productAdapter = ProductAdapter {}

        recycler_view_order_product_list.adapter = productAdapter

        orderViewModel.getOrderProducts(orderId.toInt()).observe(this) {
            setUpViews(it)
        }

        fab_create_order_product.setOnClickListener {
            showOrderProductDialog()
        }
    }

    private fun setUpViews(it: List<Product>) {
        if (it.isNullOrEmpty()) {
            swipe_container.visibility = View.GONE
            recycler_view_order_product_list.visibility = View.GONE
            linearLayoutCompat.visibility = View.VISIBLE
        } else {
            swipe_container.isRefreshing = false
            swipe_container.visibility = View.VISIBLE
            linearLayoutCompat.visibility = View.GONE
            productAdapter.submitList(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        onBackPressed()
        return true
    }

    private fun showOrderProductDialog() {

        dialog = Dialog(this@OrderProductActivity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.dialog_create_product)

        val window: Window? = dialog!!.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val buttonClose: Button = dialog!!.findViewById(R.id.buttonClose) as Button
        val buttonOk: Button = dialog!!.findViewById(R.id.buttonOk) as Button

        val editTextProductName: EditText =
            dialog!!.findViewById(R.id.editTextProductName) as EditText
        val editTextProductPrice: EditText =
            dialog!!.findViewById(R.id.editTextProductPrice) as EditText
        val editTextProductUOM: EditText =
            dialog!!.findViewById(R.id.editTextProductUOM) as EditText

        buttonOk.setOnClickListener {
            val name = editTextProductName.text.toString().trim()
            val price = editTextProductPrice.text.toString().trim()
            val uom = editTextProductUOM.text.toString().trim()
            createOrderProduct(name, price, uom)
        }

        buttonClose.setOnClickListener { dialog!!.dismiss() }

        dialog!!.show()
    }

    private fun createOrderProduct(name: String, price: String, uom: String) {

//        val progressDialog = makeProgressDialog("Creating Order...")
//        progressDialog.show()

        lifecycleScope.launch {
            when (val orderResult =
                orderViewModel.createOrderProduct(
                    CreateOrderProduct(
                        orderId.toInt(),
                        name,
                        price,
                        uom
                    )
                )) {
                is NetworkResult.Success -> {

                    val intent = Intent(applicationContext, OrderActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                is NetworkResult.ServerError -> {
                    makeSnackbar(
                        orderResult.errorBody?.message ?: "Failed to create order product..."
                    )
                }
                NetworkResult.NetworkError -> {
                    makeSnackbar("A network error occurred. Please try again later.")
                }
                NetworkResult.Loading -> {
                }
            }
        }
    }
}
