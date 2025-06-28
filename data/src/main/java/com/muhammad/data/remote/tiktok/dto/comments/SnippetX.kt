package com.muhammad.data.remote.tiktok.dto.comments


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SnippetX(
    @SerialName("authorChannelId")
    val authorChannelId: AuthorChannelId?,
    @SerialName("authorChannelUrl")
    val authorChannelUrl: String?,
    @SerialName("authorDisplayName")
    val authorDisplayName: String?,
    @SerialName("authorProfileImageUrl")
    val authorProfileImageUrl: String?,
    @SerialName("canRate")
    val canRate: Boolean?,
    @SerialName("channelId")
    val channelId: String?,
    @SerialName("likeCount")
    val likeCount: Int?,
    @SerialName("publishedAt")
    val publishedAt: String?,
    @SerialName("textDisplay")
    val textDisplay: String?,
    @SerialName("textOriginal")
    val textOriginal: String?,
    @SerialName("updatedAt")
    val updatedAt: String?,
    @SerialName("videoId")
    val videoId: String?,
    @SerialName("viewerRating")
    val viewerRating: String?
)