package com.smartzone.diva_wear.ui.products.product_details


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.renderscript.BaseObj
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.R
import com.smartzone.diva_wear.data.pojo.BasObject
import com.smartzone.diva_wear.data.pojo.Product

import com.smartzone.diva_wear.databinding.ActivityProductDetailsBinding
import com.smartzone.diva_wear.ui.base.BaseActivity
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.ui.dailogs.AddCartDialog
import com.smartzone.diva_wear.ui.dailogs.ColorDialog
import com.smartzone.diva_wear.ui.dailogs.SizeDialog
import com.smartzone.diva_wear.ui.main.MainActivity
import com.smartzone.diva_wear.ui.products.ProductViewModel
import com.smartzone.diva_wear.ui.products.ProductsAdapterProductDetails
import com.smartzone.diva_wear.utilis.CATEGORY_ID
import com.smartzone.diva_wear.utilis.ID_KEY
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {
    val viewModel: ProductViewModel by viewModel()
    lateinit var binding: ActivityProductDetailsBinding
    val idProduct by lazy {
        intent.extras?.getString(ID_KEY, "1")
    }

    //    val idCategory by lazy {
//        intent.extras?.getString(CATEGORY_ID, "1")
//    }
    val cart by lazy {
        MyApp.getCart()
    }

    var size_id: Int = 0
    var color_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewDataBinding()
        observeProduct()
        binding.recycleProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.back.setOnClickListener {
            onBackPressed()
        }
        binding.notification.setOnClickListener {
            openNotification()
        }
        viewModel.getProductById(idProduct!!)
        observeProducts()
        binding.cart.setOnClickListener {
            goToCart()
        }

    }


    override fun getLayoutId(): Int {
        return R.layout.activity_product_details
    }

    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    fun addToCart(product: Product) {
        product.size_id=size_id
        product.color_id=color_id
        cart.addProductCart(product)
        cart.save()
        binding.countCart.text = "${cart.getCartCount()}"
        AddCartDialog(this).show(clickBuy = {
            goToCart()
        }, clickAnotherProduct = {
            val intent=MainActivity.getIntent(this)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
    }

    fun observeProduct() {
        viewModel.product.observe(this, Observer { product ->
            binding.etSize.setOnClickListener {
                SizeDialog(this).show(product.size) { size ->
                    binding.etSize.setText(size.name)
                    size_id = size.id.toInt()
                }
            }
            binding.etColor.setOnClickListener {
                ColorDialog(this).show(product.color) { color ->
                    binding.etColor.setText(color.name)
                    color_id = color.id.toInt()
                }
            }
            viewModel.getProductsSimilar(product.category_id, idProduct!!)
            binding.addCart.setOnClickListener {
                if (product.size.isNotEmpty() && size_id == 0) {
                    Toast.makeText(this, getString(R.string.mustSelectSize), Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
                if (product.color.isNotEmpty() && color_id == 0) {
                    Toast.makeText(this, getString(R.string.mustSelectColor), Toast.LENGTH_LONG)
                        .show()
                    return@setOnClickListener
                }
                addToCart(product)
            }
            binding.countCart.text = "${cart.getCartCount()}"
            if (product.images.isNotEmpty()) {
                binding.pager.adapter = SlidderProductAdapter(product.images)
                TabLayoutMediator(binding.tabLayoutDots, binding.pager) { tab, position ->
//            tab.text = "OBJECT ${(position + 1)}"
                }.attach()
            }
            binding.nameOfProduct.text = product.name_ar
            binding.description.text = product.description_ar

            binding.count.text = "${product.quantity}"
            if (product.favourite) {
                binding.like.setImageResource(R.drawable.likeactive)
            } else {
                binding.like.setImageResource(R.drawable.like)
            }
            if(product.sale != "0"){
                binding.priceSalled.text=product.sale
                binding.priceSalled.visibility= View.VISIBLE
                binding.price.text=product.price
                binding.price.background= ContextCompat.getDrawable(this,R.drawable.line_drawaable)
            }else{
                binding.price.text=product.price
                binding.priceSalled.visibility= View.GONE
                binding.price.background=null
            }
            val prod = cart.getProductFromCart(idProduct!!)
            prod?.let {
                size_id = prod.size_id
                color_id = prod.size_id
                setNameCalorAndSize(prod.size, prod.color)
            }
            binding.plus.setOnClickListener {
                binding.count.text = "${cart.addproduct(product)}"
                // binding.countCart.text="${cart.orderBean.listProduct.size}"
            }
            binding.minus.setOnClickListener {
                binding.count.text = "${cart.removeProduct(product)}"
                // binding.countCart.text="${ca}"
            }
        })


    }

    private fun goToCart() {
        startActivity(
            MainActivity.getIntent(this)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("cart", true)
        )
    }

    fun observeProducts() {
        viewModel.lisProducts.observe(this, Observer {
            binding.recycleProduct.adapter = ProductsAdapterProductDetails(it, { product ->
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

    fun setNameCalorAndSize(
        listSize: List<BasObject>,
        listColor: List<BasObject>
    ) {
        for (item in listSize) {
            if (item.id == size_id.toString()) {
                binding.etSize.setText(item.name)
                break
            }
        }
        for (item in listColor) {
            if (item.id == color_id.toString()) {
                binding.etColor.setText(item.name)
                break
            }
        }
    }
}