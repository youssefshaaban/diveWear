package com.smartzone.diva_wear.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.lifecycle.Observer
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.databinding.ActivityLoginBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.forget_password.ForgetPasswordActivity
import com.smartzone.diva_wear.ui.main.MainActivity
import com.smartzone.diva_wear.ui.register.RegisterActivity
import com.smartzone.diva_wear.utilis.SavePrefs
import org.koin.androidx.viewmodel.ext.android.viewModel

    class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    val viewModel: LoginViewModel by viewModel()
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=getViewDataBinding()
        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etPhone.text.toString(),binding.etPassword.text.toString())
        }
        observerUser()

        binding.tittlePress.setOnClickListener {
            startActivity(RegisterActivity.getIntent(this))
        }
        binding.tittleForgetPassword.setOnClickListener {
            startActivity(ForgetPasswordActivity.getIntent(this))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    companion object{
        fun getIntent(context: Context):Intent= Intent(context,LoginActivity::class.java)
    }

    fun observerUser(){
        viewModel.user.observe(this, Observer {
            SavePrefs(this,User::class.java).save(it)
            MyApp.getApp().appPreferencesHelper.setLogin(true)
            startActivity(MainActivity.getIntent(this)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK )
            )
            finishAffinity()
        })
    }
}