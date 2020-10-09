package com.smartzone.diva_wear.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.AppPreferencesHelper
import com.smartzone.diva_wear.ui.login.LoginActivity
import com.smartzone.diva_wear.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(mainLooper).postDelayed({
            if (MyApp.getApp().appPreferencesHelper.isLogin()){
                startActivity(MainActivity.getIntent(this))
                finish()
            }else{
                startActivity(LoginActivity.getIntent(this))
                finish()
            }
        },1000)
    }
}