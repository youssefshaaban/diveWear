package com.smartzone.diva_wear.ui.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.databinding.ItemProductBinding
import com.smartzone.diva_wear.utilis.AppUtils
import com.smartzone.diva_wear.utilis.CartManger

class ProductsAdapter(
    val products: List<Product>,
    val click: (Product) -> Unit,
    val clickFavourite: (String,Int)->Unit
) :
    RecyclerView.Adapter<ProductsAdapter.SingleRow>() {

    val cartManager=MyApp.getCart()

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
            view.nameOfProduct.text = product.name_ar
            view.price.text = product.price
            view.count.text="${product.quantity}"
            product.image?.let {
                AppUtils.loadGlideImage(
                    view.root.context,
                    it,
                    R.drawable.default_image,
                    view.imageProduct
                )
            }
            if (product.favourite)
                view.like.setImageResource(R.drawable.likee)
            else
                view.like.setImageResource(R.drawable.likeeee)
            view.root.setOnClickListener {
                click(product)
            }
            if(product.sale != "0"){
                view.priceSalled.text=product.sale
                view.priceSalled.visibility=View.VISIBLE
                view.price.text=product.price
                view.price.background=ContextCompat.getDrawable(view.root.context,R.drawable.line_drawaable)
            }else{
                view.price.text=product.price
                view.priceSalled.visibility=View.GONE
                view.price.background=null
            }
            view.cart.setOnClickListener {
                if (cartManager.addProductCart(product)){
                    Toast.makeText(view.root.context,view.root.context.getString(R.string.doneAddCart),Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(view.root.context,view.root.context.getString(R.string.isAlerdyExsist),Toast.LENGTH_LONG).show()
                }
                notifyItemChanged(layoutPosition)

            }
            view.plus.setOnClickListener {
                product.quantity=cartManager.addproduct(product)
                notifyItemChanged(layoutPosition)
            }
            view.minus.setOnClickListener {
                product.quantity=cartManager.removeProduct(product)
                notifyItemChanged(layoutPosition)
            }
            view.like.setOnClickListener {
                clickFavourite(product.id,layoutPosition)
            }

        }
    }


}