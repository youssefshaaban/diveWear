package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.User

data class ResponseUser(
    val status: Boolean,
    val user: User?,
    val message:String?
)