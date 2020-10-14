package com.smartzone.diva_wear.ui.login

import androidx.lifecycle.MutableLiveData
import com.google.firebase.iid.FirebaseInstanceId
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.repositery.AccountRepositery
import com.smartzone.diva_wear.data.repositery.FavouriteRepositery
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CartManger
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class LoginViewModel(
    private val accountRepositery: AccountRepositery,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val user=MutableLiveData<User>()
    fun login(userName:String,password:String){
        setLoading(true)
        val token = FirebaseInstanceId.getInstance().token
        add {
            accountRepositery.login(userName,password,token)
                .with(schedulerProvider).subscribe({
                    if (it.status){
                        user.value=it.user
                    }else{
                        setErrorMessage(it.message)
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }
}