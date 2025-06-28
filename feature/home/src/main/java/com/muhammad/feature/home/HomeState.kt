package com.muhammad.feature.home

import com.muhammad.data.domain.model.Video
import com.muhammad.reeltime.utils.UiText

data class HomeState(
    val shortVideos : List<Video> = listOf(),
    val videoPage : Int = 1,
    val shortVideoError : String?=null,
    val trendingShortVideoError : String?=null,
    val trendingVideos : List<Video> = listOf(),
    val trendingVideoPage : Int= 1,
    val isShortVideosLoading : Boolean = false,
    val isMoreShortVideosLoading : Boolean = false,
    val isMoreTrendingShortVideosLoading : Boolean = false,
    val isTrendingShortVideosLoading : Boolean = false,
)