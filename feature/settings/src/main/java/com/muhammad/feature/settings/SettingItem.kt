package com.muhammad.feature.settings

import androidx.annotation.*
import androidx.compose.runtime.Composable

data class SettingItem(
    @get:DrawableRes val icon : Int,
    @get:StringRes val title : Int,
    val trailing :@Composable (() -> Unit)?=null
)
