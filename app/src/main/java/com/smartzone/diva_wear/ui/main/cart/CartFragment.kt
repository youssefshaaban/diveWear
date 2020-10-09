package com.smartzone.diva_wear.ui.main.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Cart
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.databinding.FragmentCartBinding
import com.smartzone.diva_wear.ui.base.BaseFragment
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.main.MainActivity
import com.smartzone.diva_wear.ui.order_details.OrderDetailsActivity
import com.smartzone.diva_wear.utilis.CartManger
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.ParsePosition

class CartFragment : BaseFragment<FragmentCartBinding>() {


    lateinit var cart: CartManger
    lateinit var binding: FragmentCartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=getViewDataBinding()
        binding.recycleCategories.layoutManager=LinearLayoutManager(activity)
        cart=MyApp.getCart()
        binding.totalPrice.text="${cart.calculatePrice()}"
        binding.notification.setOnClickListener {
            (activity as MainActivity).openNotification()
        }
        binding.recycleCategories.adapter=CartAdapter(cart.getCartList(),{
            deleteProduct->
            clickDelete(product = deleteProduct)
        },clickPlus = {
            product: Product, position: Int ->
            clickPlus(product, position)
        },clickMinus={
            product: Product, position: Int ->
            clickMinus(product, position)
        })
        binding.doneBuy.setOnClickListener {
            if (!cart.IsEmpty()){
                cart.save()
                startActivity(OrderDetailsActivity.getIntent(binding.getRoot().context))
            }else{
                Toast.makeText(activity,getString(R.string.thereIsNoProduct),Toast.LENGTH_LONG).show()
            }

        }
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_cart
    }

    override fun getViewModel(): BaseViewModel? {
        return null
    }

    private fun clickMinus(product: Product,position: Int){
        cart.removeProduct(product)
        binding.recycleCategories.adapter?.notifyItemChanged(position)
        binding.totalPrice.text="${cart.calculatePrice()}"
    }
    private fun clickPlus(product: Product,position: Int){
        cart.addproduct(product)
        binding.recycleCategories.adapter?.notifyItemChanged(position)
        binding.totalPrice.text="${cart.calculatePrice()}"
    }
    private fun clickDelete(product: Product){
        cart.removeFromCart(product)
        binding.recycleCategories.adapter?.notifyDataSetChanged()
        binding.totalPrice.text="${cart.calculatePrice()}"
    }
}