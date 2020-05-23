package co.shara.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import co.shara.R
import co.shara.data.model.Product
import kotlinx.android.synthetic.main.row_order_product_item.view.*

object ProductViewDiffer : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.product_id == newItem.product_id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}

typealias ProductProductViewClickListener = (Product) -> Unit

internal class ProductAdapter(
    private val listener: ProductProductViewClickListener
) : ListAdapter<Product, ProductAdapter.ViewHolder>(ProductViewDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_order_product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewProductName: TextView = itemView.textViewProductName
        private val textViewProductPrice: TextView = itemView.textViewProductPrice
        private val textViewProductUOM: TextView = itemView.textViewProductUOM

        fun bind(consignment: Product, listener: ProductProductViewClickListener) {
            textViewProductName.text = "Ajab Home Baking Flour"
            textViewProductPrice.text = "KES: 1,450"
            textViewProductUOM.text = "1kg x 24 packets"

            itemView.setOnClickListener {
                listener.invoke(consignment)
            }
        }
    }
}