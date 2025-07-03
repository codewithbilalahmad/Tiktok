package com.muhammad.feature.cameramedia

sealed interface CameraMediaEvent{
    data object FetchTemplates : CameraMediaEvent
}