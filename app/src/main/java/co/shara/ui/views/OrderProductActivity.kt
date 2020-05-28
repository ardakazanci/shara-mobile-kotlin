package co.shara.ui.views

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.shara.R
import co.shara.data.model.Product
import co.shara.ui.adapter.ProductAdapter
import co.shara.ui.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order_product.*
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

        buttonClose.setOnClickListener { dialog!!.dismiss() }

        dialog!!.show()
    }
}
