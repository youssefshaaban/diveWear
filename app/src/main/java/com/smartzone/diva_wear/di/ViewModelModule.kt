package com.smartzone.diva_wear.di

import com.smartzone.diva_wear.ui.forget_password.ForgetPassordViewModel
import com.smartzone.diva_wear.ui.login.LoginViewModel
import com.smartzone.diva_wear.ui.main.home.HomeViewModel
import com.smartzone.diva_wear.ui.products.ProductViewModel
import com.smartzone.diva_wear.ui.register.RegisterViewModel
import com.smartzone.diva_wear.utilis.rx.ApplicationSchedulerProvider
import com.smartzone.diva_wear.utilis.rx.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModleModule= module {
    viewModel { HomeViewModel(get(),get()) }
    viewModel { ProductViewModel(get(),get(),get()) }
    viewModel { LoginViewModel(get(),get()) }
    viewModel { RegisterViewModel(get(),get(),get()) }
    viewModel { ForgetPassordViewModel(get(),get()) }
}
val rxModule = module {
    factory { ApplicationSchedulerProvider() as SchedulerProvider }
}
