package com.smartzone.diva_wear.data.repositery

import com.smartzone.diva_wear.data.APIHelper
import com.smartzone.diva_wear.data.pojo.Category
import com.smartzone.diva_wear.data.reponse.ResponseCategory
import com.smartzone.diva_wear.data.reponse.ResponseSliders
import com.smartzone.diva_wear.data.utils.Result
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.functions.Function

class HomeRepository(private val apiHelper: APIHelper){

    fun getCategories():Observable<Result<List<Category>>>{
        return apiHelper.getCategories().flatMap(io.reactivex.functions.Function<ResponseCategory, ObservableSource<out Result<List<Category>>>> { response ->
            if (response.status){
                return@Function Observable.just(Result.Success(response.categories))
            }else{
                return@Function Observable.just(Result.Failure("No Result"))
            }
        })
    }
    fun getSlider():Single<ResponseSliders>{
        return apiHelper.getSlider()
    }


}