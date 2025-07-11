package com.muhammad.tiktok.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.muhammad.common.theme.R

enum class BottomBarDestination(
    val route : Destinations,
    @get:StringRes val title : Int?=null,
    @get:DrawableRes val unFilledIcon : Int,
    @get:DrawableRes val filledIcon : Int?=null,
    @get:DrawableRes val darkModeIcon : Int? = null
){
    HOME(
        route = Destinations.HomeScreen,
        title = R.string.home,
        unFilledIcon = R.drawable.ic_home,
        filledIcon = R.drawable.ic_home_fill
    ),

    FRIENDS(
        route = Destinations.FriendsScreen,
        title = R.string.friends,
        unFilledIcon = R.drawable.ic_friends,
        filledIcon = R.drawable.ic_friends
    ),

    ADD(
        route = Destinations.CameraMediaScreen,
        unFilledIcon = R.drawable.ic_add_dark,
        darkModeIcon = R.drawable.ic_add_light
    ),

    INBOX(
        route = Destinations.InboxScreen,
        title = R.string.inbox,
        unFilledIcon = R.drawable.ic_inbox,
        filledIcon = R.drawable.ic_inbox_fill
    ),

    Profile(
        route = Destinations.MyProfileScreen,
        title = R.string.profile,
        unFilledIcon = R.drawable.ic_profile,
        filledIcon = R.drawable.ic_profile_fill
    ),
}