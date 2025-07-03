package com.muhammad.feature.creatorprofile.di

import com.muhammad.feature.creatorprofile.CreatorProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val creatorProfileModule = module {
    viewModelOf(::CreatorProfileViewModel)
}