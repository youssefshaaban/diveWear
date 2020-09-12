package com.smartzone.diva_wear.ui.main.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.databinding.ItemCartViewBinding
import com.smartzone.diva_wear.databinding.ItemCategoryViewBinding
import com.smartzone.diva_wear.utilis.AppUtils

class CartAdapter(
    val cart: List<Product>,
    val clickDelete:(Product)->Unit,
    val clickPlus:(Product,Int)->Unit,
    val clickMinus:(Product,Int)->Unit
) :
    RecyclerView.Adapter<CartAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): CartAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_cart_view, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return cart.size
    }


    override fun onBindViewHolder(holder: CartAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemCartViewBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val product = cart[pos]
            view.tvName.text=product.name_ar
            view.tvPrice.text=product.price
            view.count.text="${product.quantity}"
            AppUtils.loadGlideImage(view.root.context,product.image,R.drawable.default_image,view.imageProduct)
            view.plus.setOnClickListener {
                clickPlus(product,layoutPosition)
            }
            view.minus.setOnClickListener {
                clickMinus(product,layoutPosition)
            }
            view.delete.setOnClickListener {
                clickDelete(product)
            }
        }
    }


}