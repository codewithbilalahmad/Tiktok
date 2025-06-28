package com.muhammad.data.domain.model

data class Comment(
    val profileId : String,
    val username : String,
    val comment : String,
    val profileImageUrl : String,
    val likeCount : Int,
    val commentedAt : String
)
