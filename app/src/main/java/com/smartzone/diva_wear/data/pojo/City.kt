package com.smartzone.diva_wear.data.pojo

data class City(
    val id: String,
    val name: String,
    val price: String
){
    override fun toString(): String {
        return name
    }
}