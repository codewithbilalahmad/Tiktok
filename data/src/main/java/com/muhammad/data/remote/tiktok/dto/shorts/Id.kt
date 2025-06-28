package com.muhammad.data.remote.tiktok.dto.shorts


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Id(
    @SerialName("kind")
    val kind: String?,
    @SerialName("videoId")
    val videoId: String?
)