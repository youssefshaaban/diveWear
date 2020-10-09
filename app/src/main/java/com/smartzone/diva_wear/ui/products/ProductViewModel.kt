package com.smartzone.diva_wear.ui.products

import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.repositery.FavouriteRepositery
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.CartManger
import com.smartzone.diva_wear.utilis.SavePrefs
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class ProductViewModel(
    private val productRepositery: ProductRepositery,
    private val favouriteRepositery: FavouriteRepositery,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val lisProducts = MutableLiveData<List<Product>>()
    val noitfyPosition = SingleLiveEvent<Int>()
    val product = MutableLiveData<Product>()

    fun getProducts(categoryId: String, search: String? = null, sort: String? = null) {
        setLoading(true)
        add {
            productRepositery.getProducts(category_id = categoryId, search = search, sort = sort)
                .with(schedulerProvider)
                .subscribe({
                    if (it.status) {
                        setQuentityWithCart(it.products)
                        lisProducts.value = it.products
                    } else {
                        lisProducts.value = ArrayList()
                    }
                    setLoading(false)
                }, {
                    setErrorNetwork(it)
                })
        }
    }

    fun getProductsSimilar(categoryId: String,idProduct: String) {
        add {
            productRepositery.getProductSimilar(category_id = categoryId,product_id = idProduct)
                .with(schedulerProvider)
                .subscribe({
                    if (it.status) {
                        setQuentityWithCart(it.products)
                        lisProducts.value = it.products
                    } else {
                        lisProducts.value = ArrayList()
                    }
                }, {
                    setErrorNetwork(it)
                })
        }
    }

    private fun setQuentityWithCart(products:List<Product>){
        val cart = MyApp.getCart()
        for (itm in products) {
            if (cart.orderBean.listProduct.contains(itm)) {
                val index = cart.orderBean.listProduct.indexOf(itm)
                itm.quantity = cart.orderBean.listProduct[index].quantity
            }else{
                itm.quantity=1
            }
        }
    }
    fun getProductById(idProduct: String) {
        setLoading(true)
        add {
            productRepositery.getProducts(id = idProduct).with(schedulerProvider)
                .subscribe({
                    if (it.status) {
                        setQuentityWithCart(it.products)
                        if (it.products.isNotEmpty())
                            product.value = it.products[0]
                    } else {
                        lisProducts.value = ArrayList()
                    }
                    setLoading(false)
                }, {
                    setErrorNetwork(it)
                })
        }
    }

    fun addFavourite(idProduct: String, index: Int?) {
        setLoading(true)
        val user=SavePrefs(MyApp.getApp(),User::class.java).load()
        add {
            favouriteRepositery.add_favourite(user_id = user?.id!!,id =  idProduct)
                .with(schedulerProvider).subscribe({
                    if (it.status && index != null) {
                        lisProducts.value?.let { products ->
                            products[index].favourite = it.favourite
                            noitfyPosition.value = index
                        }
                    } else {
                        setErrorMessage("error")
                    }
                    setLoading(false)
                }, {
                    setErrorNetwork(it)
                })
        }
    }

}