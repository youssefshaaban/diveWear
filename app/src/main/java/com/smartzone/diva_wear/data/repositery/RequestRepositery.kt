package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.BaseObjectResponse
import com.smartzone.diva_wear.data.reponse.RequestResponse
import com.smartzone.diva_wear.data.reponse.ResponseFavourite
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import io.reactivex.Single

class RequestRepositery(private val apiHelper: APIHelper){
    fun getcart(user_id:String,request:String?,id:String?):Single<RequestResponse>{
        return apiHelper.getRequests(user_id,request,id)
    }
}