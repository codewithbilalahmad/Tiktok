package com.muhammad.data.remote.tiktok.mapper.creatorProfile

import com.muhammad.data.domain.model.User
import com.muhammad.data.remote.tiktok.dto.createrProfile.Item

fun Item.toUser(): User {
    val userId = id.toString()
    return User(
        userId = userId,
        username = snippet?.customUrl.orEmpty(),
        fullname = snippet?.title.orEmpty(),
        profileImage = snippet?.thumbnails?.default?.url.orEmpty(),
        views = statistics?.viewCount?.toLong() ?: 0L,
        videos = statistics?.videoCount?.toInt() ?: 0,
        bio = snippet?.description.orEmpty(),
        followers = statistics?.subscriberCount?.toLong() ?: 0L, isVerified = true
    )
}