package com.smartzone.diva_wear.ui.product_details


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.Slider

import com.smartzone.diva_wear.databinding.ActivityProductDetailsBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.dailogs.AddCartDialog
import com.smartzone.diva_wear.ui.main.MainActivity
import com.smartzone.diva_wear.ui.main.home.SlidderAdapter
import com.smartzone.diva_wear.ui.products.ProductViewModel
import com.smartzone.diva_wear.ui.products.ProductsAdapter
import com.smartzone.diva_wear.utilis.CATEGORY_ID
import com.smartzone.diva_wear.utilis.ID_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {
    val viewModel: ProductViewModel by viewModel()
    lateinit var binding: ActivityProductDetailsBinding
    val idProduct by lazy {
        intent.extras?.getString(ID_KEY, "1")
    }
    val idCategory by lazy {
        intent.extras?.getString(CATEGORY_ID, "1")
    }
    val cart by lazy {
        MyApp.getCart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        observeProduct()
        binding.recycleProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        viewModel.getProductById(idProduct!!)
        viewModel.getProductsSimilar(idCategory!!, idProduct!!)
        observeProducts()

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_product_details
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    fun observeProduct() {
        viewModel.product.observe(this, Observer { product ->
            binding.addCart.setOnClickListener {
                cart.addProductCart(product)
                binding.countCart.text="${cart.getCartCount()}"
                AddCartDialog(this).show(clickBuy={
                    startActivity(MainActivity.getIntent(this)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .putExtra("cart",true)
                    )
                },clickAnotherProduct= {MainActivity.getIntent(this)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)})
            }
             binding.countCart.text="${cart.getCartCount()}"
            if (product.images.isNotEmpty()) {
                binding.pager.adapter = SlidderProductAdapter(product.images)
                TabLayoutMediator(binding.tabLayoutDots, binding.pager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
                }.attach()
            }
            binding.nameOfProduct.text = product.name_ar
            binding.description.text = product.description_ar
            binding.price.text = product.price
            binding.count.text = "${product.quantity}"
            if (product.favourite) {
                binding.like.setImageResource(R.drawable.likeactive)
            } else {
                binding.like.setImageResource(R.drawable.like)
            }
            binding.plus.setOnClickListener {
                binding.count.text="${cart.addproduct(product)}"
               // binding.countCart.text="${cart.orderBean.listProduct.size}"
            }
            binding.minus.setOnClickListener {
                binding.count.text="${cart.removeProduct(product)}"
               // binding.countCart.text="${ca}"
            }
        })


    }

    fun observeProducts() {
        viewModel.lisProducts.observe(this, Observer {
            binding.recycleProduct.adapter = ProductsAdapter(it, { product ->
                val cart = MyApp.getCart()
                cart.save()
                startActivity(ProductDetailsActivity.getIntent(this)
                    .apply {
                        putExtra(CATEGORY_ID, product.category_id)
                        putExtra(ID_KEY, product.id)
                    }
                )
                finish()
            }, { idProduct: String, position: Int ->
                viewModel.addFavourite(idProduct, position)
            })
        })
    }

    companion object {
        fun getIntent(context: Context): Intent =
            Intent(context, ProductDetailsActivity::class.java)
    }
}