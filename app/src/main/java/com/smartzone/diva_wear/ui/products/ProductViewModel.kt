package com.smartzone.diva_wear.ui.products

import androidx.lifecycle.MutableLiveData
import com.smartzone.diva_wear.data.pojo.Product
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import com.smartzone.diva_wear.ui.base.BaseViewModel
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import com.smartzone.diva_wear.utilis.rx.with

class ProductViewModel(
    private val productRepositery: ProductRepositery,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val lisProducts = MutableLiveData<List<Product>>()
    fun getProducts(categoryId: String,search:String?=null) {
        setLoading(true)
        add {
            productRepositery.getProducts(categoryId,search).with(schedulerProvider)
                .subscribe({
                    if (it.status) {
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

}