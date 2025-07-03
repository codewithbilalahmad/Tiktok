package com.muhammad.feature.cameramedia.di

import com.muhammad.feature.cameramedia.CameraMediaViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val cameraMediaModule = module {
    viewModelOf(::CameraMediaViewModel)
}