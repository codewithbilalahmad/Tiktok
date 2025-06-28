package com.muhammad.data.di

import com.muhammad.data.remote.templates.repository.TemplateRepositoryImp
import com.muhammad.data.remote.tiktok.repository.TikTokRepositoryImp
import org.koin.dsl.module

val dataModule = module{
    single { TikTokRepositoryImp(get()) }
    single { TemplateRepositoryImp(get()) }
}