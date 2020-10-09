package com.smartzone.diva_wear.ui.main.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.reponse.Request
import com.smartzone.diva_wear.data.repositery.RequestRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.SavePrefs
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class RequestsViewModel(private val requestRepositery: RequestRepositery, private val schedulerProvider: SchedulerProvider) : BaseViewModel() {

    val sentRequest=MutableLiveData<List<Request>>()
    val request=MutableLiveData<Request>()
    val finishRequest=MutableLiveData<List<Request>>()

    fun getSentRrequest(){
        setLoading(true)
        val user=SavePrefs(MyApp.getApp(),User::class.java).load()
        add {
            requestRepositery.getcart(user_id = user!!.id,request = "0",id = null).with(schedulerProvider)
                .subscribe({
                    if (it.status){
                        sentRequest.value=it.requests
                    }else{
                        finishRequest.value=ArrayList()
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }

    fun getFinishRrequest(){
        setLoading(true)
        val user=SavePrefs(MyApp.getApp(),User::class.java).load()
        add {
            requestRepositery.getcart(user_id = user!!.id,request = "1",id = null).with(schedulerProvider)
                .subscribe({
                    if (it.status){
                        finishRequest.value=it.requests
                    }else{
                        finishRequest.value=ArrayList()
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }
    fun getRequestById(id:String?){

        setLoading(true)
        val user=SavePrefs(MyApp.getApp(),User::class.java).load()
        add {
            requestRepositery.getcart(user_id = user!!.id,request = null,id = id).with(schedulerProvider)
                .subscribe({
                    if (it.status&&it.requests.isNotEmpty()) {
                        request.value = it.requests[0]
                    }
                    setLoading(false)
                },{
                    setErrorNetwork(it)
                })
        }
    }
}