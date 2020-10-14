package com.smartzone.diva_wear.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.City
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.databinding.ActivityRegisterBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.dailogs.CityDialog
import com.smartzone.diva_wear.ui.main.MainActivity
import com.smartzone.diva_wear.utilis.SavePrefs
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.regex.Pattern


class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    val viewModel: RegisterViewModel by viewModel()
    lateinit var binding: ActivityRegisterBinding
    var selectCity:City?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        viewModel.getCity()
        binding.etCity.setOnClickListener {
            binding.etCity.setOnClickListener {
                viewModel.cities.value?.let {
                    CityDialog(this).show(it,false){
                        selectCity=it
                        binding.etCity.setText(selectCity?.name)
                    }
                }?:run{
                    viewModel.getCity()
                }
            }
        }
        binding.btnSignUp.setOnClickListener {
            if (validateInput()) {
                val registerData=User(binding.etName.text.toString(),binding.etPassword.text.toString(),"+2${binding.etPhone.text}",selectCity?.id)
                startActivity(VerficationActivity.getIntent(this).apply {
                    putExtra("data",registerData)
                })
            }
        }
        binding.tittlePress.setOnClickListener {
            finish()
        }
        //observerUser()
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
        if (binding.etPhone.text.isNotBlank()&&!isValidPhoneNumber(binding.etPhone.text.toString())){
            binding.etPhone.error = getString(R.string.enter_valid_phone)
            return false
        }
        if (binding.etPassword.text.isBlank()) {
            binding.etPassword.error = getString(R.string.field_required)
            return false
        }
        if (selectCity==null){
            Toast.makeText(this,getString(R.string.mustSelectCity),Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }


    fun isValidPhoneNumber(
        phoneNumber: String
    ): Boolean {
        val regexEg = "^(010|011|012|015)[0-9]{8}$"
        return Pattern.matches(regexEg, phoneNumber)
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