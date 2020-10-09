package com.smartzone.diva_wear.data.pojo

import java.io.Serializable

data class Product(
    val category_id: String,
    val created: String?=null,
    val description: String?=null,
    val description_ar: String?=null,
    var favourite: Boolean,
    val homepage: String,
    val id: String,
    val image: String?=null,
    val images: List<String>,
    val name: String,
    val name_ar: String,
    val price: String,
    val sale: String,
    val status: String,
    var quantity:Int=1,
    var isAddCart:Boolean=false
):Serializable{
    override fun equals(other: Any?): Boolean {
        if (other is Product && other.id == this.id) return true
        return false
    }
}