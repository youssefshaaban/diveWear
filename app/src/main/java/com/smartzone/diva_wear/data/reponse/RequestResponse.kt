package com.smartzone.diva_wear.data.reponse

data class RequestResponse(
    val count: Int,
    val requests: List<Request>,
    val status: Boolean
)