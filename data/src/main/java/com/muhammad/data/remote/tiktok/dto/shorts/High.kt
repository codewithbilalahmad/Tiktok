package com.muhammad.data.remote.tiktok.dto.shorts


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class High(
    @SerialName("height")
    val height: Int?,
    @SerialName("url")
    val url: String?,
    @SerialName("width")
    val width: Int?
)