package co.shara.ui.views

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import co.shara.data.retrofit.CreateOrder
import co.shara.network.NetworkResult
import co.shara.ui.adapter.OrderAdapter
import co.shara.ui.model.OrderSummary
import co.shara.ui.viewmodel.OrderViewModel
import co.shara.util.makeSnackbar
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderActivity : AppCompatActivity() {

    private val orderViewModel: OrderViewModel by viewModel()
    private var dialog: Dialog? = null

    private lateinit var orderAdapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val itemDecor = DividerItemDecoration(this, LinearLayout.VERTICAL)
        val layoutManager = LinearLayoutManager(this)
        recycler_view_order_list.layoutManager = layoutManager
        recycler_view_order_list.addItemDecoration(itemDecor)

        refreshDataAndSync()

        orderAdapter = OrderAdapter { order ->
            val intent = Intent(this, OrderProductActivity::class.java)
            intent.putExtra("order_id", order.orderNumber)
            startActivity(intent)
        }

        recycler_view_order_list.adapter = orderAdapter

        orderViewModel.getOrders().observe(this) {
            setUpViews(it)
        }

        fab_create_order.setOnClickListener {
            showOrderDialog()
        }
    }

    private fun refreshDataAndSync() {
        val refreshJob =
            orderViewModel.fetchMyOrders()
        refreshJob.invokeOnCompletion {
            swipe_container.isRefreshing = false
            recycler_view_order_list.smoothScrollToPosition(0)
        }
    }

    private fun setUpViews(it: List<OrderSummary>) {
        if (it.isNullOrEmpty()) {
            swipe_container.visibility = View.GONE
            recycler_view_order_list.visibility = View.GONE
            linearLayoutCompat.visibility = View.VISIBLE
        } else {
            swipe_container.isRefreshing = false
            linearLayoutCompat.visibility = View.GONE
            swipe_container.visibility = View.VISIBLE
            orderAdapter.submitList(it)
        }
    }

    private fun showOrderDialog() {

        dialog = Dialog(this@OrderActivity)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setCancelable(false)
        dialog!!.setContentView(R.layout.dialog_create_order)

        val window: Window? = dialog!!.window
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val buttonClose: Button = dialog!!.findViewById(R.id.buttonClose) as Button
        val buttonOk: Button = dialog!!.findViewById(R.id.buttonOk) as Button
        val editTextOrderNumber: EditText =
            dialog!!.findViewById(R.id.editTextOrderNumber) as EditText

        buttonOk.setOnClickListener {
            val orderNumber = editTextOrderNumber.text.toString().trim()

            if (TextUtils.isEmpty(orderNumber)) {
                editTextOrderNumber.error = "Order Number is required"
                return@setOnClickListener
            }

            createOrder(orderNumber)

            dialog!!.dismiss()
        }

        buttonClose.setOnClickListener { dialog!!.dismiss() }

        dialog!!.show()
    }

    private fun createOrder(orderNumber: String) {

//        val progressDialog = makeProgressDialog("Creating Order...")
//        progressDialog.show()

        lifecycleScope.launch {
            when (val orderResult =
                orderViewModel.createOrder(
                    CreateOrder(
                        orderNumber
                    )
                )) {
                is NetworkResult.Success -> {

                    val intent = Intent(applicationContext, OrderActivity::class.java)
                    startActivity(intent)
                    finish()

                }
                is NetworkResult.ServerError -> {
                    makeSnackbar(orderResult.errorBody?.message ?: "Failed to create order...")
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
