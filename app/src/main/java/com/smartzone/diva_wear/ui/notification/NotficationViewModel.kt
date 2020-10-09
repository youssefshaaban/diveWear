package com.smartzone.diva_wear.ui.notification

import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.data.pojo.Notification
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.repositery.*
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CartManger
import com.smartzone.diva_wear.utilis.SavePrefs
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class NotficationViewModel(
    private val notifcationRepositery: NotifcationRepositery,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val notfications=MutableLiveData<List<Notification>>()
    fun getNotifcations(
    ){
        setLoading(true)
        val user=SavePrefs(MyApp.getApp(),User::class.java).load()
        add {
            notifcationRepositery.getNotification(user_id = user?.id)
                .with(schedulerProvider).subscribe({
                    if (it.status){
                        notfications.value=it.notifications
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }

}