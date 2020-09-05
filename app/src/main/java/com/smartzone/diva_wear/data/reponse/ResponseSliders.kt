package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.Slider

data class ResponseSliders(
    val count: Int,
    val sliders: List<Slider>,
    val status: Boolean
)