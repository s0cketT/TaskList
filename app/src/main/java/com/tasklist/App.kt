package com.tasklist

import android.app.Application
import com.tasklist.di.databaseModule
import com.tasklist.di.appModule
import com.tasklist.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(appModule, networkModule, databaseModule)
        }
    }
}
