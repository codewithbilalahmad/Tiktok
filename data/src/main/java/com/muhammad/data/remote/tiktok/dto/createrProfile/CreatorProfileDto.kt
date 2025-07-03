package com.muhammad.data.remote.tiktok.dto.createrProfile


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatorProfileDto(
    @SerialName("etag")
    val etag: String?,
    @SerialName("items")
    val items: List<Item> = emptyList(),
    @SerialName("kind")
    val kind: String?,
    @SerialName("pageInfo")
    val pageInfo: PageInfo?
)