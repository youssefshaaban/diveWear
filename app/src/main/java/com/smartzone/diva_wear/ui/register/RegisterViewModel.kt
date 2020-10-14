package com.smartzone.diva_wear.ui.register

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

class RegisterViewModel(
    private val accountRepositery: AccountRepositery,
    private val schedulerProvider: SchedulerProvider,
    private val generalRepositery: GeneralRepositery
) : BaseViewModel() {

    val user=MutableLiveData<User>()
    val cities=MutableLiveData<List<City>>()
    fun signUP(mobile: String,
               password:String,
               token:String?,
               name:String,
               city_id:String?
    ){
        setLoading(true)
        add {
            accountRepositery.register(mobile,password,null,name,city_id)
                .with(schedulerProvider).subscribe({
                    if (it.status){
                        user.value=it.user
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }

    fun getCity(){
        setLoading(true)
        add {
            generalRepositery.getCity()
                .with(schedulerProvider).subscribe({
                    if (it.status){
                        cities.value=it.cities
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }
}