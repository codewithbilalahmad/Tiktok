package com.muhammad.feature.loginwithemailphone

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    var email: TextFieldState = TextFieldState(),
    val phoneNumber: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val settledPage : Int?=null,
    val dialCode : String = "PK +92",
    val suggestedDomains: List<String> = listOf(
        "@gmail.com",
        "@hotmail.com",
        "@outlook.com",
        "@yahoo.com",
        "@icloud.com"
    )
)