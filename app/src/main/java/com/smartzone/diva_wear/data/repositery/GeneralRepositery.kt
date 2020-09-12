package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.ResponseCity
import com.smartzone.diva_wear.data.reponse.ResponseFavourite
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import com.smartzone.diva_wear.data.reponse.ResponseUser
import io.reactivex.Single

class GeneralRepositery(private val apiHelper: APIHelper){
    fun getCity():Single<ResponseCity>{
        return apiHelper.getCity()
    }
}