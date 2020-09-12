package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.ResponseFavourite
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import io.reactivex.Single

class FavouriteRepositery(private val apiHelper: APIHelper){
    fun add_favourite(user_id:String,id:String):Single<ResponseFavourite>{
        return apiHelper.addFavourite(user_id,id = id)
    }
}