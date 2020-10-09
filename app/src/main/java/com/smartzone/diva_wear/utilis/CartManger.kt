package com.smartzone.diva_wear.utilis

import com.smartzone.diva_wear.MyApp
import com.smartzone.diva_wear.data.pojo.Cart
import com.smartzone.diva_wear.data.pojo.Product

class CartManger() {

    var orderBean: Cart = SavePrefs(MyApp.getApp(), Cart::class.java).load() ?: run { Cart() }

    fun addproduct(product: Product): Int {
        var count = 1
        val index = orderBean.listProduct.indexOf(product)
        if (orderBean.listProduct.contains(product)) {
            val pro = orderBean.listProduct[index]
            count = pro.quantity + 1
            pro.quantity = count
        } else {
            orderBean.listProduct.add(product)
        }
        return count
    }

    fun addProductCart(product: Product): Boolean {
        var count = 1
        val index = orderBean.listProduct.indexOf(product)
        if (orderBean.listProduct.contains(product)) {
            val pro = orderBean.listProduct[index]
            return if (!pro.isAddCart) {
                pro.isAddCart = true
                true
            } else
                false
        } else {
            product.isAddCart = true
            product.quantity = count
            orderBean.listProduct.add(product)
            return true
        }
    }

    fun removeFromCart(product: Product) {
        orderBean.listProduct.remove(product)
    }

    fun removeProduct(product: Product): Int {
        var count = 1
        val index = orderBean.listProduct.indexOf(product)
        if (orderBean.listProduct.contains(product)) {
            val pro = orderBean.listProduct[index]
            if (pro.quantity - 1 == 0) {
                if (!pro.isAddCart){
                    count = 1
                    orderBean.listProduct.removeAt(index)
                }else{
                    count=1
                }
            } else {
                count = pro.quantity - 1
                pro.quantity = count
            }
        }
        return count
    }

    fun clearOrder() {
        orderBean.listProduct.clear()
        orderBean.delviry=null
        save()
    }

    fun save() {
        SavePrefs(MyApp.getApp(), Cart::class.java).save(orderBean)
    }

    fun calculatePrice(): Float {
        var total: Float = 0.0f;
        orderBean.listProduct.forEach {
            if (it.isAddCart)
                total += (it.quantity * it.price.toFloat())
        }
        return total
    }

    fun getCartList(): ArrayList<Product> {
        val list = ArrayList<Product>()
        orderBean.listProduct.forEach {
            if (it.isAddCart)
                list.add(it)
        }
        orderBean.listProduct = list
        save()
        return list
    }

    fun getCartCount(): Int {
        var count = 0
        orderBean.listProduct.forEach {
            if (it.isAddCart)
                count++
        }
        return count
    }
    fun IsEmpty():Boolean{
        return getCartList().isEmpty()
    }
}