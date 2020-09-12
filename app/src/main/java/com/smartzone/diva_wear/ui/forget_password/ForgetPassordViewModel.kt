package com.smartzone.diva_wear.ui.forget_password

import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.repositery.AccountRepositery
import com.smartzone.diva_wear.data.repositery.FavouriteRepositery
import com.smartzone.diva_wear.data.repositery.GeneralRepositery
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CartManger
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class ForgetPassordViewModel(
    private val accountRepositery: AccountRepositery,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val success=MutableLiveData<Boolean>()
    fun forgetPassword(  mobile: String
    ){
        setLoading(true)
        add {
            accountRepositery.forgetPassword(mobile)
                .with(schedulerProvider).subscribe({
                    if (it.status){
                        success.value=true
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }

    fun onClickConfirm(){

    }
}