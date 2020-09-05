package com.smartzone.diva_wear.data.reponse

import com.smartzone.diva_wear.data.pojo.Category
import java.io.Serializable

data class ResponseCategory( val categories: List<Category>, val count: Int,
                            val status: Boolean):Serializable