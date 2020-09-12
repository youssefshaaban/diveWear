package com.smartzone.diva_wear.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.databinding.ActivityRegisterBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.login.LoginActivity
import com.smartzone.diva_wear.ui.main.MainActivity
import com.smartzone.diva_wear.utilis.SavePrefs
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    val viewModel: RegisterViewModel by viewModel()
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        viewModel.getCity()
        observeCity()
        binding.btnSignUp.setOnClickListener {
            if (validateInput()) {
                val city = binding.etCity.selectedItem as City
                viewModel.login(
                    binding.etPhone.text.toString(), binding.etPassword.text.toString()
                    , null, binding.etName.text.toString(), city.id
                )
            }
        }
        binding.tittlePress.setOnClickListener {
            finish()
        }
        observerUser()
    }

    private fun validateInput(): Boolean {
        if (binding.etName.text.isBlank()) {
            binding.etName.error = getString(R.string.field_required)
            return false
        }
        if (binding.etPhone.text.isBlank()) {
            binding.etPhone.error = getString(R.string.field_required)
            return false
        }
        if (binding.etPassword.text.isBlank()) {
            binding.etPassword.error = getString(R.string.field_required)
            return false
        }
        return true
    }

    fun observeCity() {
        viewModel.cities.observe(this, Observer {
            val adapter: CityAdapter = CityAdapter(this, android.R.layout.simple_spinner_item, it)
            binding.etCity.adapter = adapter
        })
    }

    fun observerUser() {
        viewModel.user.observe(this, Observer {
            SavePrefs(this, User::class.java).save(it)
            MyApp.getApp().appPreferencesHelper.setLogin(true)
            startActivity(
                MainActivity.getIntent(this)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            )
            finishAffinity()
        })
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, RegisterActivity::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }
}