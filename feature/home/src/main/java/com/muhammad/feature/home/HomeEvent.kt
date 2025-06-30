package com.muhammad.feature.home

sealed interface HomeEvent{
    data class OnCommentChange(val comment : String) : HomeEvent
    data object OnToggleCommentBottomSheet : HomeEvent
    data class OnCommentClick(val videoId : String) : HomeEvent
    data object OnPaginateShortVideos : HomeEvent
    data object OnPaginateTrendingShortVideos : HomeEvent
    data object OnPaginateComments : HomeEvent
    data class LoadComments(val videoId : String) : HomeEvent
}