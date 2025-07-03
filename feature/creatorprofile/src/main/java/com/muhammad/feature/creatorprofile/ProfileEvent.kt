package com.muhammad.feature.creatorprofile

sealed interface ProfileEvent{
    data object LoadUserData : ProfileEvent
    data object LoadUserShortVideos : ProfileEvent
    data object PaginateUserShortVideos : ProfileEvent
    data object ToggleProfileVideoSection : ProfileEvent
    data class OnProfileVideoClick(val index : Int) : ProfileEvent
}