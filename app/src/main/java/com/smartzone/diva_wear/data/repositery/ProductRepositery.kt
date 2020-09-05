package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import io.reactivex.Single

class ProductRepositery(private val apiHelper: APIHelper){
    fun getProducts(category_id:String?=null,search:String?=null,id:String?=null):Single<ResponseProducts>{
        return apiHelper.getProducts(category_id = category_id,search = search,id = id)
    }
}