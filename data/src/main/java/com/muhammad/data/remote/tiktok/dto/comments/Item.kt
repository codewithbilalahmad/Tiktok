package com.muhammad.data.remote.tiktok.dto.comments


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("etag")
    val etag: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("kind")
    val kind: String?,
    @SerialName("snippet")
    val snippet: Snippet?
)