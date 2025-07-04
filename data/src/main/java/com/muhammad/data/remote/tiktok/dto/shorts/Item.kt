package com.muhammad.data.remote.tiktok.dto.shorts


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("etag")
    val etag: String?,
    @SerialName("id")
    val id: Id?,
    @SerialName("kind")
    val kind: String?,
    @SerialName("snippet")
    val snippet: Snippet?
)
@Serializable
data class TrendingItem(
    @SerialName("etag")
    val etag: String?,
    @SerialName("id")
    val id: String,
    @SerialName("kind")
    val kind: String?,
    @SerialName("snippet")
    val snippet: Snippet?
)