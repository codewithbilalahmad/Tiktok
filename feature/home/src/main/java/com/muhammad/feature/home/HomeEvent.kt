package com.muhammad.feature.home

sealed interface HomeEvent{
    data object OnPaginateShortVideos : HomeEvent
    data object OnPaginateTrendingShortVideos : HomeEvent
}