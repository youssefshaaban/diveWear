package com.smartzone.diva_wear.ui.confirm_order

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.repositery.CarRepositery
import com.smartzone.diva_wear.data.repositery.FavouriteRepositery
import com.smartzone.diva_wear.data.repositery.GeneralRepositery
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CartManger
import com.smartzone.diva_wear.utilis.SavePrefs
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class ConfirmOrderDetailsViewModel(
    private val cartRepositery: CarRepositery,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    val success=MutableLiveData<Boolean>()

    fun addCart(){
        val cart=MyApp.getCart()
        val map=HashMap<String,String>()
        val list=cart.getCartList()
        for ((index,value) in list.withIndex()){
            map.set("product_id[$index]","${value.id}")
            Log.e("TagValue","")
            map.set("quantity[$index]","${value.quantity}")
        }
        setLoading(true)
        val user=SavePrefs(MyApp.getApp(),User::class.java).load()
        add {
            cartRepositery.add_cart(user?.id!!,cart.orderBean.delviry!!.id,map).with(schedulerProvider)
                .subscribe({
                    if (it.status){
                        success.value=true
                    }else{
                        setErrorMessage("Some thingError")
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }

}