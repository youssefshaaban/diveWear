package com.smartzone.diva_wear.utilis

import android.content.Context
import android.content.SharedPreferences

import com.google.gson.Gson

class SavePrefs<T>(activity: Context, cls: Class<T>) {
    private val USER_PREFS_FILE_NAME: String
    private val DATA = "DATA"
    private var prefs: SharedPreferences? = null
    private val cls: Class<*>

    init {
        this.cls = cls
        USER_PREFS_FILE_NAME = cls.name
        prefs = activity.getSharedPreferences(
            USER_PREFS_FILE_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun save(obj: T) {
        val editor = prefs!!.edit()
        val data = Gson().toJson(obj)
        editor.putString(DATA, data)
        editor.apply()
    }

    fun load(): T? {
        val data = prefs!!.getString(DATA, "")
        var objectConvert:T?
        try {
             objectConvert=(Gson().fromJson<T>(data, cls) as T)
        } catch (ex: Exception) {
            return null
        }
        return objectConvert
    }


    fun clear() {
        prefs!!.edit().clear().apply()
    }

}
