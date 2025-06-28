package com.muhammad.data.remote.tiktok.dto.createrProfile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thumbnails(
    @SerialName("default")
    val default: Default?,
    @SerialName("high")
    val high: High?,
    @SerialName("medium")
    val medium: Medium?
)