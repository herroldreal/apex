package com.apex.apextest.di

import com.apex.apextest.views.main.MainViewModel
import com.apex.apextest.views.splash.SplashViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { SplashViewModel() }
    single { MainViewModel(get(), get()) }
}