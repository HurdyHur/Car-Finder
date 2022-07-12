package com.harry.carfinder

import android.app.Application
import com.harry.carfinder.di.searchModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class CarFinderApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CarFinderApplication)
            modules(searchModule)
        }
    }
}
