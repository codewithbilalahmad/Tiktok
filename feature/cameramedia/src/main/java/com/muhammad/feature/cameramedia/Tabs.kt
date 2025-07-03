package com.muhammad.feature.cameramedia

import androidx.annotation.StringRes
import com.muhammad.common.theme.R

enum class Tabs(
    @get:StringRes val title : Int
){
    CAMERA(R.string.camera),
    STORY(R.string.story),
    TEMPLATES(R.string.templates)
}