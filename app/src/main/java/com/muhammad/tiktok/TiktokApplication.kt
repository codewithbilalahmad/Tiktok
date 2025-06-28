package com.muhammad.tiktok

import android.app.Application
import com.muhammad.core.di.coreModule
import com.muhammad.data.di.dataModule
import com.muhammad.feature.home.di.homeModule
import com.muhammad.tiktok.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TiktokApplication : Application(){
    companion object{
        lateinit var INSTANCE : TiktokApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin{
            androidContext(this@TiktokApplication)
            androidLogger()
            modules(appModule,coreModule, dataModule, homeModule)
        }
    }
}