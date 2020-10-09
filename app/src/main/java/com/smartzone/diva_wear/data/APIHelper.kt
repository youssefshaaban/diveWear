package com.smartzone.diva_wear.data

import com.smartzone.diva_wear.data.reponse.*
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.ArrayList

interface APIHelper {

    @GET("main/get_category")
    fun getCategories(): Observable<ResponseCategory>

    @GET("main/get_home_sliders")
    fun getSlider(): Single<ResponseSliders>

    @GET("main/get_products")
    fun getProducts(
        @Query("category_id") category_id: String? = null,
        @Query("search") search: String? = null,
        @Query("id") id: String? = null,
        @Query("product_id") product_id: String? = null,
        @Query("user_id") user_id: String? = null,
        @Query("sort") sort: String? = "low"
    ): Single<ResponseProducts>

    @GET("global/get_favourite")
    fun getFavourite(
        @Query("search") search: String? = null,
        @Query("user_id") user_id: String
    ): Single<ResponseProducts>

    @GET("global/add_favourite")
    fun addFavourite(
        @Query("user_id") user_id: String,
        @Query("id") id: String
    ): Single<ResponseFavourite>

    @GET("global/login")
    fun login(
        @Query("mobile") mobile: String,
        @Query("password") password: String,
        @Query("token") token: String?
    ): Single<ResponseUser>

    @GET("global/cities")
    fun getCity(): Single<ResponseCity>

    @GET("global/signup")
    fun signUp(
        @Query("mobile") mobile: String,
        @Query("password") password: String,
        @Query("token") token: String?,
        @Query("name") name: String,
        @Query("city_id") city_id: String?
    ): Single<ResponseUser>

    @GET("global/edit")
    fun edit(
        @Query("mobile") mobile: String,
        @Query("name") name: String,
        @Query("city_id") city_id: String?,
        @Query("id") id: String,
        @Query("image") image: String?
    ): Single<ResponseUser>

    @GET("global/forget_password")
    fun forgetPassword(
        @Query("mobile") mobile: String
    ): Single<BaseObjectResponse>

    @GET("main/add_cart_multiple")
    fun add_cart_multiple(
        @Query("user_id") user_id: String,
        @Query("city_id") city_id:String,
        @QueryMap meta: Map<String, String>
    ): Single<BaseObjectResponse>

    @GET("main/get_cart")
    fun getRequests(
        @Query("user_id") user_id: String?,
        @Query("request") request: String?,
        @Query("id") id: String?
    ): Single<RequestResponse>

    @GET("main/get_notifications")
    fun getNotifications(
        @Query("user_id") user_id: String?
    ): Single<ResponseNotification>

    @POST("global/upload_image_api")
    @Multipart
    fun uploadFiles(
        @Part parts: List<MultipartBody.Part>
    ): Single<ResponseUploadImage>

    @GET("global/settings")
    fun getSettings(
    ): Single<ResponseSetting>

    @GET("global/send_feedback")
    fun sendFeedBack(
        @Query("mobile") mobile: String,
        @Query("name") name: String,
        @Query("message") message: String?
    ): Single<ResponseSendFedback>

}