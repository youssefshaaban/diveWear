package com.smartzone.diva_wear.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.databinding.ItemProductBinding
import com.smartzone.diva_wear.utilis.AppUtils

class ProductsAdapter(
    val products: List<Product>,
    val click: (Product) -> Unit

) :
    RecyclerView.Adapter<ProductsAdapter.SingleRow>() {


    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): ProductsAdapter.SingleRow {
        return SingleRow(
            DataBindingUtil.inflate(
                LayoutInflater.from(p0.context),
                R.layout.item_product, p0, false
            )
        )


    }

    override fun getItemCount(): Int {
        return products.size
    }


    override fun onBindViewHolder(holder: ProductsAdapter.SingleRow, p1: Int) {
        holder.bind(p1)
    }


    inner class SingleRow(var view: ItemProductBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bind(pos: Int) {
            val product = products[pos]
            view.nameOfProduct.text=product.name_ar
            view.price.text=product.price
            product.image?.let {
                AppUtils.loadGlideImage(view.root.context,it,R.drawable.default_image,view.imageProduct)
            }

            view.root.setOnClickListener {
                click(product)
            }

        }
    }


}