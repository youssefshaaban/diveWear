package com.smartzone.diva_wear.ui.main.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.pojo.User
import com.smartzone.diva_wear.data.repositery.FavouriteRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.SavePrefs
import com.smartzone.diva_wear.utilis.SingleLiveEvent
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class FavouriteViewModel(private val favouriteRepositery: FavouriteRepositery,private val schedulerProvider: SchedulerProvider) : BaseViewModel() {

    val lisProducts = MutableLiveData<List<Product>>()
    val noitfyPosition = SingleLiveEvent<Int>()
    fun getFavourite(user_id: String,search:String?=null) {
        setLoading(true)
        add {
            favouriteRepositery.get_favourite(user_id,search)
                .with(schedulerProvider)
                .subscribe({
                    if (it.status) {
                        it.products.forEach {
                            it.favourite=true
                        }
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
    fun addFavourite(idProduct: String, index: Int?) {
        setLoading(true)
        val user= SavePrefs(MyApp.getApp(), User::class.java).load()
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