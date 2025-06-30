package com.muhammad.data.remote.tiktok.dto.comments


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentsDto(
    @SerialName("etag")
    val etag: String?,
    @SerialName("items")
    val items: List<Item>?,
    @SerialName("kind")
    val kind: String?,
    @SerialName("nextPageToken")
    val nextPageToken: String?=null,
    @SerialName("pageInfo")
    val pageInfo: PageInfo?
)