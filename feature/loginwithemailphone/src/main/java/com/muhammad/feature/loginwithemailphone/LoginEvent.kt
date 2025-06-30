package com.muhammad.feature.loginwithemailphone

sealed interface LoginEvent{
    data class OnPageChange(val settledPage : Int) : LoginEvent
}