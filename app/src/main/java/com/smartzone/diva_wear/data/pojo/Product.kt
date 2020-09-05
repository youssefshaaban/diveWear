package com.smartzone.diva_wear.data.pojo

data class Product(
    val category_id: String,
    val created: String?=null,
    val description: String?=null,
    val description_ar: String?=null,
    val favourite: Boolean,
    val homepage: String,
    val id: String,
    val image: String?=null,
    val images: List<String>,
    val name: String,
    val name_ar: String,
    val price: String,
    val sale: String,
    val status: String
)