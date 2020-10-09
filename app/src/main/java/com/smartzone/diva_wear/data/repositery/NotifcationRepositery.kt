package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.*
import io.reactivex.Single

class NotifcationRepositery(private val apiHelper: APIHelper){
    fun getNotification(user_id:String?):Single<ResponseNotification>{
        return apiHelper.getNotifications(user_id)
    }
}