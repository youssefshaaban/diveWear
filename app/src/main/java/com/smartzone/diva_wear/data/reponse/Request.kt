package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.Product
import java.io.Serializable

data class Request(
    val address: String,
    val counts: Int,
    val created: String,
    val id: String,
    val latitude: String,
    val longitude: String,
    val notifiied: String,
    val price: Int,
    val products: List<Product>? = null,
    val status: String,
    val user_id: String,
    val Product:Product?,
    val shipment: Int=0
):Serializable