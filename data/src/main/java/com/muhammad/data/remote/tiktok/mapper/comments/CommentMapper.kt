package com.muhammad.data.remote.tiktok.mapper.comments

import com.muhammad.core.toFormattedDate
import com.muhammad.data.domain.model.Comment
import com.muhammad.data.remote.tiktok.dto.comments.Item

fun Item.toComment(): Comment {
    return Comment(
        profileId = snippet?.channelId.orEmpty(),
        username = snippet?.topLevelComment?.snippet?.authorDisplayName.orEmpty(),
        profileImageUrl = snippet?.topLevelComment?.snippet?.authorProfileImageUrl.orEmpty(),
        comment = snippet?.topLevelComment?.snippet?.textOriginal.orEmpty(),
        likeCount = snippet?.topLevelComment?.snippet?.likeCount ?: 0,
        commentedAt = snippet?.topLevelComment?.snippet?.publishedAt.orEmpty().toFormattedDate()
    )
}