package com.muhammad.data.remote.tiktok.dto.comments


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorChannelId(
    @SerialName("value")
    val value: String?
)