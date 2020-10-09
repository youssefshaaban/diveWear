package com.smartzone.diva_wear.ui.order_details

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.lifecycle.Observer
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.databinding.ActivityOrderDetailsBinding
import com.smartzone.diva_wear.ui.MapsActivity
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.confirm_order.ConfirmOrderDetailsActvity
import com.smartzone.diva_wear.ui.dailogs.CityDialog
import com.smartzone.diva_wear.utilis.AppUtils
import com.smartzone.diva_wear.utilis.CartManger
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderDetailsActivity : BaseActivity<ActivityOrderDetailsBinding>() {

    lateinit var binding: ActivityOrderDetailsBinding
    lateinit var cart: CartManger
    val viewModel: OrderDetailsViewModel by viewModel()
    var selectCity:City?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        cart = MyApp.getCart()
        viewModel.getCity()
        binding.price.text = "${cart.calculatePrice()}"
        observeCity()
        binding.location.setOnClickListener {
            startActivityForResult(MapsActivity.getIntent(this),1)
        }
        binding.notification.setOnClickListener {
            openNotification()
        }
        binding.back.setOnClickListener { onBackPressed() }
        binding.etCity.setOnClickListener {
            viewModel.cities.value?.let {
                CityDialog(this).show(it,true){
                    selectCity=it
                    binding.etCity.setText(selectCity?.title)
                }
            }
        }
        binding.confirm.setOnClickListener {
            if (selectCity == null||selectCity?.id.equals("-1")) {
                Toast.makeText(
                    this, getString(
                        R.string.mustDelviry
                    ), Toast.LENGTH_LONG
                ).show()
            }else{
                cart.orderBean.delviry=selectCity
                cart.save()
                startActivity(ConfirmOrderDetailsActvity.getIntent(this))
            }
        }
    }
    val handler=object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                1->{
                    val bundle=msg.data
                    binding.location.text=bundle.getString("address")
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==1&&resultCode==Activity.RESULT_OK&&data!=null){
            val mLat=data.getDoubleExtra("lat",0.0)
            val mLon=data.getDoubleExtra("lon",0.0)
            AppUtils.getAddress(handler,this,mLat,mLon)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_order_details
    }

    fun observeCity() {
        viewModel.cities.observe(this, Observer {
            val cities = it
            cities.add(0, City("-1", "اختر المكان", "", "اختر المكان"))
//            binding.etCity.adapter =
//                DeliverCityAdapter(this, android.R.layout.simple_spinner_item, cities)
        })
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, OrderDetailsActivity::class.java)
    }
}