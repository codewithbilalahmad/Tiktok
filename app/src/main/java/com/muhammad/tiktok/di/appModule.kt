package com.muhammad.tiktok.di

import com.muhammad.tiktok.TiktokApplication
import org.koin.dsl.module

val appModule = module {
    single { TiktokApplication.INSTANCE }
}