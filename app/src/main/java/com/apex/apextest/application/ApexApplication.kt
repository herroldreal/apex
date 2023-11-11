package com.apex.apextest.application

import android.app.Application
import com.apex.apextest.di.navigationModule
import com.apex.apextest.di.viewModelModule
import com.apex.apextest.utils.installTimber
import com.apex.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApexApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ApexApplication)
            modules(
                listOf(
                    dataModule,
                    viewModelModule,
                    navigationModule,
                ),
            )
        }

        installTimber()
    }
}