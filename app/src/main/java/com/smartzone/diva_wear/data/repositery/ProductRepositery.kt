package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import com.smartzone.diva_wear.utilis.SavePrefs
import io.reactivex.Single

class ProductRepositery(private val apiHelper: APIHelper){
    fun getProducts(category_id:String?=null,search:String?=null,id:String?=null,sort:String?=null):Single<ResponseProducts>{
        val user= SavePrefs(MyApp.getApp(), User::class.java).load()
        return apiHelper.getProducts(category_id = category_id,user_id = user?.id,search = search,id = id
        ,sort = sort
        )
    }

    fun getProductSimilar(category_id:String?=null,product_id:String?=null,sort:String?=null):Single<ResponseProducts>{
        val user= SavePrefs(MyApp.getApp(), User::class.java).load()
        return apiHelper.getProducts(category_id = category_id,
            product_id = product_id,user_id = user?.id
            ,sort = sort
        )
    }
}