package com.muhammad.feature.home

import com.muhammad.data.domain.model.Comment
import com.muhammad.data.domain.model.Video
import kotlin.random.Random

data class HomeState(
    val comment : String = "",
    val showCommentBottomSheet : Boolean = false,
    val shortVideos : List<Video> = listOf(),
    val videoPage : Int = 1,
    val selectedVideoId : String = "",
    val commentPage : Int = 1,
    val comments : List<Comment> = listOf(),
    val isCommentsLoading : Boolean = false,
    val isMoreCommentsLoading : Boolean = false,
    val shortVideoError : String?=null,
    val trendingShortVideoError : String?=null,
    val trendingVideos : List<Video> = listOf(),
    val trendingVideoPage : Int= 1,
    val isShortVideosLoading : Boolean = false,
    val isMoreShortVideosLoading : Boolean = false,
    val isMoreTrendingShortVideosLoading : Boolean = false,
    val isTrendingShortVideosLoading : Boolean = false,
)