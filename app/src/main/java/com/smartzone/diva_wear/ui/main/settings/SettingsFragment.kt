package com.smartzone.diva_wear.ui.main.settings

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.databinding.SettingsFragmentBinding
import com.smartzone.diva_wear.ui.contact_us.ContactUsActivity
import com.smartzone.diva_wear.ui.login.LoginActivity
import com.smartzone.diva_wear.ui.main.MainActivity
import com.smartzone.diva_wear.ui.profile.ProfileActivity
import com.smartzone.diva_wear.utilis.AppUtils
import com.smartzone.diva_wear.utilis.BASE_URL_IMAGE
import com.smartzone.diva_wear.utilis.SavePrefs

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() =
            SettingsFragment()
    }

    lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fillData()
        binding.notification.setOnClickListener {
            (activity as MainActivity).openNotification()
        }
        handleClick()
        super.onViewCreated(view, savedInstanceState)

    }

    private fun fillData() {
        val user = SavePrefs(binding.getRoot().context, User::class.java).load()
        user?.let { userData ->
            binding.name.text = userData.name
            binding.tvPhone.text = userData.mobile
            userData.image?.let { path ->
                val url = BASE_URL_IMAGE + path
                AppUtils.loadGlideImage(
                    binding.getRoot().context,
                    url,
                    R.drawable.default_image,
                    binding.profile
                )
            }
        }
    }

    private fun handleClick() {
        binding.contactus.setOnClickListener {
            activity?.let {
                startActivity(ContactUsActivity.getIntent(it))
            }

        }
        binding.contentProfile.setOnClickListener {
            startActivityForResult(ProfileActivity.getIntent(binding.getRoot().context), 1)
        }
        binding.logout.setOnClickListener {
            activity?.let {
                MyApp.getApp().appPreferencesHelper.setLogin(false)
                SavePrefs(it, User::class.java).clear()
            }
            startActivity(LoginActivity.getIntent(binding.getRoot().context).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            })
            activity?.finishAffinity()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            fillData()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}