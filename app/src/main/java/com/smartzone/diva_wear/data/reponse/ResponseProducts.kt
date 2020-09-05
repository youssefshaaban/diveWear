package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.Product

data class ResponseProducts(
    val count: Int,
    val products: List<Product>,
    val status: Boolean
)