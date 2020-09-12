package com.smartzone.diva_wear.data

import com.smartzone.diva_wear.data.reponse.*
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
        @Query("id") id:String?=null,
        @Query("product_id") product_id:String?=null,
        @Query("user_id") user_id:String?=null,
        @Query("sort") sort:String?="low"
    ): Single<ResponseProducts>

    @GET("diva/global/add_favourite")
    fun addFavourite(
        @Query("user_id") user_id: String,
        @Query("id") id:String
    ): Single<ResponseFavourite>

    @GET("diva/global/login")
    fun login(
        @Query("mobile") mobile: String,
        @Query("password") password:String,
        @Query("token") token:String?
    ): Single<ResponseUser>

    @GET("diva/global/cities")
    fun getCity():Single<ResponseCity>

    @GET("diva/global/signup")
    fun signUp(
        @Query("mobile") mobile: String,
        @Query("password") password:String,
        @Query("token") token:String?,
        @Query("name") name:String,
        @Query("city_id") city_id:String?
        ):Single<ResponseUser>

    @GET("diva/global/forget_password")
    fun forgetPassword(
        @Query("mobile") mobile: String
    ):Single<BaseObjectResponse>
}