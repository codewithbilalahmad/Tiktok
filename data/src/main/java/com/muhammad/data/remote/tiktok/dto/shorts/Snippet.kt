package com.muhammad.data.remote.tiktok.dto.shorts


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Snippet(
    @SerialName("channelId")
    val channelId: String?,
    @SerialName("channelTitle")
    val channelTitle: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("liveBroadcastContent")
    val liveBroadcastContent: String?,
    @SerialName("publishTime")
    val publishTime: String?=null,
    @SerialName("publishedAt")
    val publishedAt: String?,
    @SerialName("thumbnails")
    val thumbnails: Thumbnails?,
    @SerialName("title")
    val title: String?
)