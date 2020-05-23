package co.shara.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.shara.R
import co.shara.data.model.Order
import kotlinx.android.synthetic.main.row_order_item.view.*

object OrderViewDiffer : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.order_id == newItem.order_id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}

typealias ShipmentsViewClickListener = (Order) -> Unit

internal class OrderAdapter(
    private val listener: ShipmentsViewClickListener
) : ListAdapter<Order, OrderAdapter.ViewHolder>(OrderViewDiffer) {

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

        fun bind(consignment: Order, listener: ShipmentsViewClickListener) {
            textViewOrderNumber.text = "Order No # 0001"
            textViewProductCount.text = "2 Products"
            textViewOrderDate.text = "9 May, 2019"
            imageView.setImageResource(R.drawable.ic_sync_success)

            itemView.setOnClickListener {
                listener.invoke(consignment)
            }
        }
    }
}