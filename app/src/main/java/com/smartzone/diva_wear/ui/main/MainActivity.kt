package com.smartzone.diva_wear.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.AppPreferencesHelper
import com.smartzone.diva_wear.databinding.ActivityMainBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class MainActivity : BaseActivity<ActivityMainBinding>() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=getViewDataBinding()
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_home,
//            R.id.navigation_dashboard,
//            R.id.navigation_notifications
//        ))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        val cart=intent.extras?.getBoolean("cart")
        cart?.let {
            navController.navigate(R.id.cart)
        }
        binding.navView.setupWithNavController(navController)
        binding.addActivity.setOnClickListener {
            navController.navigate(R.id.home)
        }

        //navController.
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): BaseViewModel? {
        return null
    }

    companion object{
        fun getIntent(context: Context):Intent=Intent(context,MainActivity::class.java)
    }
}