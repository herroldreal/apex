package com.apex.apextest.di

import com.apex.apextest.navigation.NavigationManager
import org.koin.dsl.module

val navigationModule = module {
    single { NavigationManager() }
}