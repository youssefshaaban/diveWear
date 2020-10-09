package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.reponse.BaseObjectResponse
import com.smartzone.diva_wear.data.reponse.ResponseFavourite
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import com.smartzone.diva_wear.data.reponse.ResponseUser
import io.reactivex.Single
import retrofit2.http.Query

class AccountRepositery(private val apiHelper: APIHelper){
    fun login(userName:String,password:String,token:String?):Single<ResponseUser>{
        return apiHelper.login(userName,password,token)
    }

    fun register(
         mobile: String,
        password:String,
        token:String?,
        name:String,
         city_id:String?

    ):Single<ResponseUser>{
        return apiHelper.signUp(mobile,password,token,name,city_id)
    }


    fun editProfile(
        mobile: String,
        name:String,
        city_id:String?,
        id:String,
        image:String?=null
    ):Single<ResponseUser>{
        return apiHelper.edit(mobile,name,city_id,id,image)
    }


    fun forgetPassword(
        mobile: String
    ):Single<BaseObjectResponse>{
        return apiHelper.forgetPassword(mobile)
    }
}