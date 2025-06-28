package com.muhammad.data.domain.model

import com.muhammad.core.formattedCount

data class User(
    val userId: String,
    val username: String,
    val fullname: String,
    val followers: Long,
    val videos: Int,
    val views : Long,
    val bio: String,
    val profileImage: String,
    val isVerified: Boolean,
){
    var formattedViewCount = ""
    var formattedFollowersCount = ""
    init {
        formattedViewCount = views.formattedCount()
        formattedFollowersCount = followers.formattedCount()
    }
}