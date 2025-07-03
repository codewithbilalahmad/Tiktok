package com.muhammad.tiktok.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations{
    @Serializable
    data object HomeScreen : Destinations
    @Serializable
    data object CameraMediaScreen : Destinations
    @Serializable
    data class CreatorProfileScreen(val userId : String) : Destinations
    @Serializable
    data object FriendsScreen : Destinations
    @Serializable
    data object InboxScreen : Destinations
    @Serializable
    data object LoginEmailPhoneScreen : Destinations
    @Serializable
    data object MyProfileScreen : Destinations
    @Serializable
    data object SettingsScreen : Destinations
    @Serializable
    data object AuthorizationScreen : Destinations
}