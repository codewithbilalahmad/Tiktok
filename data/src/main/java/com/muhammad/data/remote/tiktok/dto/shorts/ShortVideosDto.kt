package com.muhammad.data.remote.tiktok.dto.shorts


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortVideosDto(
    @SerialName("etag")
    val etag: String?,
    @SerialName("items")
    val items: List<Item?>?,
    @SerialName("kind")
    val kind: String?,
    @SerialName("nextPageToken")
    val nextPageToken: String?,
    @SerialName("pageInfo")
    val pageInfo: PageInfo?,
    @SerialName("regionCode")
    val regionCode: String?
)