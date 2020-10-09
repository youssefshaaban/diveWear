package com.smartzone.diva_wear.di

import com.smartzone.diva_wear.ui.confirm_order.ConfirmOrderDetailsViewModel
import com.smartzone.diva_wear.ui.contact_us.ContactusViewModel
import com.smartzone.diva_wear.ui.forget_password.ForgetPassordViewModel
import com.smartzone.diva_wear.ui.login.LoginViewModel
import com.smartzone.diva_wear.ui.main.favourite.FavouriteViewModel
import com.smartzone.diva_wear.ui.main.home.HomeViewModel
import com.smartzone.diva_wear.ui.main.orders.RequestsViewModel
import com.smartzone.diva_wear.ui.notification.NotficationViewModel
import com.smartzone.diva_wear.ui.order_details.OrderDetailsViewModel
import com.smartzone.diva_wear.ui.products.ProductViewModel
import com.smartzone.diva_wear.ui.profile.ProfileViewModel
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
    viewModel { OrderDetailsViewModel(get(),get()) }
    viewModel { ConfirmOrderDetailsViewModel(get(),get()) }
    viewModel { RequestsViewModel(get(),get()) }
    viewModel { FavouriteViewModel(get(),get()) }
    viewModel { NotficationViewModel(get(),get()) }
    viewModel { ProfileViewModel(get(),get(),get()) }
    viewModel { ContactusViewModel(get(),get()) }


}
val rxModule = module {
    factory { ApplicationSchedulerProvider() as SchedulerProvider }
}
