package com.muhammad.tiktok

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.muhammad.core.di.coreModule
import com.muhammad.data.di.dataModule
import com.muhammad.feature.cameramedia.di.cameraMediaModule
import com.muhammad.feature.creatorprofile.di.creatorProfileModule
import com.muhammad.feature.home.di.homeModule
import com.muhammad.feature.loginwithemailphone.di.loginWithEmailPhoneModule
import com.muhammad.feature.settings.di.settingsModule
import com.muhammad.tiktok.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TiktokApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var INSTANCE: TiktokApplication
        @SuppressLint("StaticFieldLeak")
        private var currentActivity : Activity?=null
        fun getCurrentActivity() = currentActivity
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@TiktokApplication)
            androidLogger()
            modules(
                appModule,
                coreModule,
                dataModule,
                homeModule,
                settingsModule,
                loginWithEmailPhoneModule, cameraMediaModule, creatorProfileModule
            )
        }
        registerActivityLifecycleCallbacks(object  : ActivityLifecycleCallbacks{
            override fun onActivityCreated(activity: Activity, p1: Bundle?) {
                currentActivity = activity
            }

            override fun onActivityDestroyed(activity: Activity) {
               if(currentActivity == activity){
                   currentActivity = null
               }
            }

            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivitySaveInstanceState(
                activity: Activity,
                p1: Bundle,
            ) {
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityStopped(activity: Activity) {
            }

        })
    }
}