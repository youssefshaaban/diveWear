package com.smartzone.diva_wear.ui.contact_us

import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.reponse.Setting
import com.smartzone.diva_wear.data.repositery.AccountRepositery
import com.smartzone.diva_wear.data.repositery.FavouriteRepositery
import com.smartzone.diva_wear.data.repositery.GeneralRepositery
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CartManger
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with
import java.io.File

class ContactusViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val generalRepositery: GeneralRepositery
) : BaseViewModel() {

    val setting=MutableLiveData<Setting>()
    val success=MutableLiveData<Boolean>()
    fun getSetting(){
        setLoading(true)
        add {
            generalRepositery.getSetting()
                .with(schedulerProvider).subscribe({
                    if (it.status){
                        setting.value=it.Setting
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }

    fun sendFeadBack(name:String,mobile:String,message:String){
        setLoading(true)
        add {
            generalRepositery.sendFeadBack(mobile,name,message)
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

}