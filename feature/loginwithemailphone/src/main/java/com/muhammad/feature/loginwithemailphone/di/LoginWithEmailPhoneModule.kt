package com.muhammad.feature.loginwithemailphone.di

import com.muhammad.feature.loginwithemailphone.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val loginWithEmailPhoneModule = module {
    viewModelOf(::LoginViewModel)
}