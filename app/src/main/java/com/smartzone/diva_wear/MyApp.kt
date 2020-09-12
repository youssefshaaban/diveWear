package com.smartzone.diva_wear

import android.app.Application
import com.smartzone.diva_wear.data.AppPreferencesHelper
import com.smartzone.diva_wear.di.AppInjector
import com.smartzone.diva_wear.utilis.CartManger

import org.koin.android.ext.android.get


class MyApp : Application() {

    lateinit var appPreferencesHelper: AppPreferencesHelper

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        instance = this
        appPreferencesHelper = get()

    }


    fun getappPreferencesHelper(): AppPreferencesHelper {
        return appPreferencesHelper
    }

    companion object {
        lateinit var instance: MyApp
        var cartManager: CartManger? = null
        fun getApp(): MyApp {
            return instance
        }

        fun getCart(): CartManger {
            if (cartManager == null){
                cartManager= CartManger()
            }
            return cartManager!!
        }
    }


}