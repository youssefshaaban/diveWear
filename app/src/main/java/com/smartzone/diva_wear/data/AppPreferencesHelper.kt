package com.smartzone.diva_wear.data

import android.content.Context
import android.content.SharedPreferences
import com.smartzone.diva_wear.utilis.prefFileName

class AppPreferencesHelper(val context: Context) {
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    fun getLang():String?{
        return prefs.getString("lang","ar")
    }
    fun setLang(lang:String){
        val editor = prefs.edit()
        editor.putString("lang",lang).apply()
    }
}