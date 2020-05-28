package co.shara.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.shara.R
import co.shara.ui.model.OrderSummary
import kotlinx.android.synthetic.main.row_order_item.view.*

object OrderSummaryViewDiffer : DiffUtil.ItemCallback<OrderSummary>() {
    override fun areItemsTheSame(oldItem: OrderSummary, newItem: OrderSummary): Boolean {
        return oldItem.orderNumber == newItem.orderNumber
    }

    override fun areContentsTheSame(oldItem: OrderSummary, newItem: OrderSummary): Boolean {
        return oldItem == newItem
    }
}

typealias OrderViewClickListener = (OrderSummary) -> Unit

internal class OrderAdapter(
    private val listener: OrderViewClickListener
) : ListAdapter<OrderSummary, OrderAdapter.ViewHolder>(OrderSummaryViewDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_order_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewOrderNumber: TextView = itemView.textViewOrderNumber
        private val textViewProductCount: TextView = itemView.textViewProductCount
        private val textViewOrderDate: TextView = itemView.textViewOrderDate
        private val imageView: ImageView = itemView.imageViewSync

        @SuppressLint("SetTextI18n")
        fun bind(order: OrderSummary, listener: OrderViewClickListener) {
            textViewOrderNumber.text = "OrderSummary No # " + order.orderNumber
            textViewProductCount.text = order.totalProduct + " Products"
            textViewOrderDate.text = order.createdDate
            imageView.setImageResource(R.drawable.ic_sync_success)

            itemView.setOnClickListener {
                listener.invoke(order)
            }
        }
    }
}