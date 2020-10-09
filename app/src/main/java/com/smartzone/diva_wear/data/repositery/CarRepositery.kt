package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.BaseObjectResponse
import com.smartzone.diva_wear.data.reponse.ResponseFavourite
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import io.reactivex.Single

class CarRepositery(private val apiHelper: APIHelper){
    fun add_cart(user_id:String,city_id:String,map:Map<String,String>):Single<BaseObjectResponse>{
        return apiHelper.add_cart_multiple(user_id,city_id,map)
    }
}