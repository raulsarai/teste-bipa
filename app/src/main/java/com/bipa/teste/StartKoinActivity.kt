package com.bipa.teste

import android.app.Application
import com.bipa.teste.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin


class StartKoinActivity : Application() {
        override fun onCreate() {
            super.onCreate()
            startKoin {
                androidContext(this@StartKoinActivity)
                modules(appModule)
            }
        }
}
