package com.smartzone.diva_wear.ui.order_details

import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.repositery.FavouriteRepositery
import com.smartzone.diva_wear.data.repositery.GeneralRepositery
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CartManger
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class OrderDetailsViewModel(
    private val generalRepositery: GeneralRepositery,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {
    val cities=MutableLiveData<ArrayList<City>>()
    fun getCity(){
        setLoading(true)
        add {
            generalRepositery.getCity()
                .with(schedulerProvider).subscribe({
                    if (it.status){
                        cities.value=it.cities as ArrayList<City>
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }
}