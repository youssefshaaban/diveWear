package com.smartzone.diva_wear.ui.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.databinding.ActivityProductsBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CATEGORY_NAME
import com.smartzone.diva_wear.utilis.ID_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsActivity : BaseActivity<ActivityProductsBinding>() {

    val viewModel: ProductViewModel by viewModel()
    lateinit var binding: ActivityProductsBinding
    lateinit var categoryId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        binding.contentToolbar.tittle.text = intent.extras?.getString(CATEGORY_NAME)
        binding.empty.layoutManager = GridLayoutManager(this, 2)
        categoryId = intent.extras?.getString(ID_KEY, "1")!!
        viewModel.getProducts(categoryId)
        binding.contentToolbar.back.setOnClickListener {
            onBackPressed()
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(binding.etSearch.text.toString()))
                    viewModel.getProducts(categoryId, null)
                else
                    viewModel.getProducts(categoryId, binding.etSearch.text.toString())
                hideKeyboard()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.search.setOnClickListener {
            if (TextUtils.isEmpty(binding.etSearch.text.toString()))
                viewModel.getProducts(categoryId, null)
            else
                viewModel.getProducts(categoryId, binding.etSearch.text.toString())
            hideKeyboard()
        }
        observeListProducts()
    }

    fun observeListProducts() {
        viewModel.lisProducts.observe(this, Observer {
            binding.empty.adapter = ProductsAdapter(it) {

            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_products
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, ProductsActivity::class.java)
    }
}