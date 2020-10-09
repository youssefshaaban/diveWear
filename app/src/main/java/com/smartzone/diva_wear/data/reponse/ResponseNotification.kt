package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.Notification

data class ResponseNotification(
    val count: Int,
    val notifications: List<Notification>,
    val status: Boolean
)