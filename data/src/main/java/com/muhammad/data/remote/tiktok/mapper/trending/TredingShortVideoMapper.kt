package com.muhammad.data.remote.tiktok.mapper.trending

import com.muhammad.core.toFormattedDate
import com.muhammad.data.domain.model.Video
import com.muhammad.data.remote.tiktok.dto.shorts.TrendingItem

fun TrendingItem.toVideo(): Video {
    val videoId = this.id.orEmpty()
    return Video(
        id = videoId,
        authorDetails = Video.User(
            userId = snippet?.channelId.orEmpty(),
            username = snippet?.channelTitle.orEmpty(),
            profileImage = snippet?.thumbnails?.default?.url.orEmpty()
        ),
        videoStats = Video.VideoStats(
            like = (1_000..1_000_000).random().toLong(),
            comment = (1_000..1_000_000).random().toLong(),
            share = (1_000..1_000_000).random().toLong(),
            favourite = (1_000..1_000_000).random().toLong(),
            views = (1_000..1_000_000).random().toLong()
        ),
        videoLink = "https://www.youtube.com/watch?v=$videoId",
        description = snippet?.description.orEmpty(),
        createdAt = snippet?.publishedAt.orEmpty().toFormattedDate(),
        hasTag = extractHashtags(snippet?.description.orEmpty())
    )
}
fun extractHashtags(description: String): List<Video.HashTag> {
    val hashtagRegex = Regex("#(\\w+)")
    return hashtagRegex.findAll(description)
        .mapIndexed { index, matchResult ->
            Video.HashTag(id = index.toLong(), title = matchResult.value)
        }
        .toList()
}