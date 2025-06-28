package com.muhammad.data.di

import com.muhammad.data.domain.repository.templates.TemplateRepository
import com.muhammad.data.domain.repository.tiktok.TiktokRepository
import com.muhammad.data.remote.templates.repository.TemplateRepositoryImp
import com.muhammad.data.remote.tiktok.repository.TikTokRepositoryImp
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module{
    single { TikTokRepositoryImp(get()) }.bind<TiktokRepository>()
    single { TemplateRepositoryImp(get()) }.bind<TemplateRepository>()
}