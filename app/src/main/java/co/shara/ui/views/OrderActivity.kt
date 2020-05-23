package co.shara.ui.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import co.shara.R
import co.shara.data.model.Order
import co.shara.ui.adapter.OrderAdapter
import co.shara.ui.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.activity_order.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderActivity : AppCompatActivity() {

    private val orderViewModel: OrderViewModel by viewModel()

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
            intent.putExtra("order_id", order.order_id.toString())
            startActivity(intent)
        }

        recycler_view_order_list.adapter = orderAdapter

        orderViewModel.getOrders().observe(this) {
            setUpViews(it)
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

    private fun setUpViews(it: List<Order>) {
        if (it.isNullOrEmpty()) {
            swipe_container.visibility = View.GONE
            recycler_view_order_list.visibility = View.GONE
        } else {
            swipe_container.isRefreshing = false
            swipe_container.visibility = View.VISIBLE
            swipe_container.visibility = View.VISIBLE
            orderAdapter.submitList(it)
        }
    }
}
