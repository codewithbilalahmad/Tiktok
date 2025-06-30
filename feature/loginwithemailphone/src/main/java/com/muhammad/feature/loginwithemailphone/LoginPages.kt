package com.muhammad.feature.loginwithemailphone

import androidx.annotation.StringRes
import com.muhammad.common.theme.R

enum class LoginPages(@get:StringRes val title : Int){
    PHONE(R.string.phone),
    EMAIL_USERNAME(R.string.email_username)
}