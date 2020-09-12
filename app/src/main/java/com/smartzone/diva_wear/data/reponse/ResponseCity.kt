package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.City

data class ResponseCity(
    val cities: List<City>,
    val status: Boolean
)