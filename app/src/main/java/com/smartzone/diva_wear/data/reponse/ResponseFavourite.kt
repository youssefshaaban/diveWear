package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.Product

data class ResponseFavourite(
    val count: Int,
    val favourite:Boolean,
    val status: Boolean
)