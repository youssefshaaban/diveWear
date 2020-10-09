package com.smartzone.diva_wear.ui.congratlation_ordr

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.databinding.ActivityDoneOrderBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.main.MainActivity

class DoneOrderActivity : BaseActivity<ActivityDoneOrderBinding>() {
    lateinit var binding: ActivityDoneOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=getViewDataBinding()
        binding.goToHome.setOnClickListener {
            startActivity(MainActivity.getIntent(this).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            finish()
        }
    }

    companion object{
        fun getIntent(context: Context):Intent= Intent(context,DoneOrderActivity::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_done_order
    }

    override fun getViewModel(): BaseViewModel? {
        return null
    }
}