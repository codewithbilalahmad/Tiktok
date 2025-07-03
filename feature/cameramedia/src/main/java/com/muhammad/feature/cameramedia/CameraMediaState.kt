package com.muhammad.feature.cameramedia

import com.muhammad.data.domain.model.Template

data class CameraMediaState(
    val templatePage : Int = 1,
    val isTemplatesLoading : Boolean = false,
    val templates : List<Template>?=null
)
