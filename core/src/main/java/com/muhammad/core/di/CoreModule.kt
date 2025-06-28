package com.muhammad.core.di

import com.muhammad.core.HttpClientFactory
import org.koin.dsl.module

val coreModule = module{
    single { HttpClientFactory.build() }
}