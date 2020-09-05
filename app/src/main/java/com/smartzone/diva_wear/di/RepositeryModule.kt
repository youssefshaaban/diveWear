package com.smartzone.diva_wear.di

import com.smartzone.diva_wear.data.repositery.HomeRepository
import com.smartzone.diva_wear.data.repositery.ProductRepositery
import org.koin.dsl.module


val repositeryModule= module {
    factory { HomeRepository(get()) }
    factory { ProductRepositery(get()) }
}