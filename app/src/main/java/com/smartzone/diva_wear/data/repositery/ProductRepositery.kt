package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import io.reactivex.Single

class ProductRepositery(private val apiHelper: APIHelper){
    fun getProducts(category_id:String?=null,search:String?=null,id:String?=null,sort:String?=null):Single<ResponseProducts>{
        return apiHelper.getProducts(category_id = category_id,search = search,id = id
        ,user_id = "1"
        ,sort = sort
        )
    }

    fun getProductSimilar(category_id:String?=null,product_id:String?=null,sort:String?=null):Single<ResponseProducts>{
        return apiHelper.getProducts(category_id = category_id,
            product_id = product_id
            ,user_id = "1"
            ,sort = sort
        )
    }
}