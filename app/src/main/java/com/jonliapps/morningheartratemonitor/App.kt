package com.jonliapps.morningheartratemonitor

import android.app.Application
import com.jonliapps.morningheartratemonitor.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }

}