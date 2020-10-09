package com.smartzone.diva_wear.ui.confirm_order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.databinding.ItemCartViewBinding
import com.smartzone.diva_wear.databinding.ItemCategoryViewBinding
import com.smartzone.diva_wear.databinding.ItemProductConfirmBinding
import com.smartzone.diva_wear.utilis.AppUtils

class OrderAdapter(
    val cart: List<Product>
) :
    RecyclerView.Adapter<OrderAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): OrderAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_product_confirm, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return cart.size
    }


    override fun onBindViewHolder(holder: OrderAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemProductConfirmBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val product = cart[pos]
            view.tvName.text=product.name_ar
            view.tvPrice.text=product.price
            view.quantity.text="${product.quantity}"
            AppUtils.loadGlideImage(view.root.context,product.image,R.drawable.default_image,view.imageProduct)
        }
    }


}