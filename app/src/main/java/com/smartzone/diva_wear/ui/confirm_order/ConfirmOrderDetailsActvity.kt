package com.smartzone.diva_wear.ui.confirm_order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.databinding.ActivityConfirmOrderDetailsBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.congratlation_ordr.DoneOrderActivity
import com.smartzone.diva_wear.utilis.CartManger
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConfirmOrderDetailsActvity : BaseActivity<ActivityConfirmOrderDetailsBinding>() {

    val viewModel:ConfirmOrderDetailsViewModel by viewModel()
    lateinit var binding: ActivityConfirmOrderDetailsBinding
    lateinit var cart:CartManger
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=getViewDataBinding()
        binding.recycleProduct.layoutManager=LinearLayoutManager(this)
        cart=MyApp.getCart()
        binding.back.setOnClickListener { onBackPressed() }
        binding.recycleProduct.adapter=OrderAdapter(cart.getCartList())
        binding.totalPrice.text="${ cart.calculatePrice()}"
        binding.deliveryPrice.text="${cart.orderBean.delviry?.price}"
        binding.allPrice.text="${(cart.calculatePrice()+cart.orderBean.delviry?.price?.toFloat()!!)}"
        binding.location.text="${cart.orderBean.delviry?.name}"
        binding.confirmButton.setOnClickListener {
            viewModel.addCart()
        }
        observeSuccces()
    }

    fun observeSuccces(){
        viewModel.success.observe(this, Observer {
            if (it){
                cart.clearOrder()
                startActivity(DoneOrderActivity.getIntent(this))
            }
        })
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_confirm_order_details
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    companion object{
        fun getIntent(context: Context):Intent=Intent(context,ConfirmOrderDetailsActvity::class.java)
    }
}