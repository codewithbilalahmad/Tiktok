package com.muhammad.data.remote.tiktok.dto.trending

import com.muhammad.data.remote.tiktok.dto.shorts.Item
import com.muhammad.data.remote.tiktok.dto.shorts.PageInfo
import com.muhammad.data.remote.tiktok.dto.shorts.TrendingItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingShortVideosDto(
    @SerialName("etag")
    val etag: String?,
    @SerialName("items")
    val items: List<TrendingItem?>?,
    @SerialName("kind")
    val kind: String?,
    @SerialName("nextPageToken")
    val nextPageToken: String?,
    @SerialName("pageInfo")
    val pageInfo: PageInfo?
)