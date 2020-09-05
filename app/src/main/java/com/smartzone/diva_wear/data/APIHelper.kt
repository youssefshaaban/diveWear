package com.smartzone.diva_wear.data

import com.smartzone.diva_wear.data.reponse.ResponseCategory
import com.smartzone.diva_wear.data.reponse.ResponseFavourite
import com.smartzone.diva_wear.data.reponse.ResponseProducts
import com.smartzone.diva_wear.data.reponse.ResponseSliders
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.ArrayList

interface APIHelper {

    @GET("diva/main/get_category")
    fun getCategories(): Observable<ResponseCategory>

    @GET("diva/main/get_home_sliders")
    fun getSlider(): Single<ResponseSliders>

    @GET("diva/main/get_products")
    fun getProducts(
        @Query("category_id") category_id: String?=null,
        @Query("search") search: String?=null,
        @Query("id") id:String?=null
    ): Single<ResponseProducts>

    @GET("diva/global/add_favourite")
    fun addFavourite(
        @Query("user_id") user_id: String?=null,
        @Query("id") id:String?=null
    ): Single<ResponseFavourite>
}