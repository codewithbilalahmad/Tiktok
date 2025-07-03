package com.muhammad.feature.creatorprofile

import com.muhammad.data.domain.model.User
import com.muhammad.data.domain.model.Video

data class ProfileState(
    val user : User?=null,
    val isLoading : Boolean = false,
    val selectedProfileVideosIndex : Int?=null,
    val showProfileVideosSection : Boolean = false,
    val userVideosPage : Int = 1,
    val userShortVideos : List<Video> = listOf(),
    val isUserShortVideoLoading : Boolean = false,
    val isMoreUserShortVideoLoading : Boolean = false,
)