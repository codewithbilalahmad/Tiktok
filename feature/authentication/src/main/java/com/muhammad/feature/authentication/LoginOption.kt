package com.muhammad.feature.authentication

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.muhammad.common.theme.PrimaryColor
import com.muhammad.common.theme.R

enum class LoginOption(
    @get:DrawableRes val icon : Int,
    @get:StringRes val title : Int,
    val containerColor : Color = Color.Transparent,
    val contentColor : Color = Color.White
){
    PHONE_EMAIL_USERNAME(
        icon = R.drawable.ic_profile,
        title = R.string.use_phone_email_username,
        containerColor = PrimaryColor
    ),
    FACEBOOK(
        icon = R.drawable.ic_facebook,
        title = R.string.continue_with_facebook,
    ),
    GOOGLE(
        icon = R.drawable.ic_google,
        title = R.string.continue_with_google,
    ),
    TWITTER(
        icon = R.drawable.ic_twitter,
        title = R.string.continue_with_twitter,
    ),
}