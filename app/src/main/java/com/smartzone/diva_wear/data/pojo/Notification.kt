package com.smartzone.diva_wear.data.pojo

import com.smartzone.diva_wear.data.pojo.Invoice

data class Notification(
    val created: String,
    val date: String,
    val id: String,
    val invoice: Invoice,
    val invoice_id: String,
    val modified: String,
    val title: String,
    val type: String,
    val updated: String,
    val user_id: String
)