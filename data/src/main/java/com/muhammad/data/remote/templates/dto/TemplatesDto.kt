package com.muhammad.data.remote.templates.dto

import kotlinx.serialization.Serializable

@Serializable
data class UnsplashSearchResponse(
    val results: List<UnsplashItem>
)

@Serializable
data class UnsplashItem(
    val id: String?,
    val description: String?,
    val alt_description: String?,
    val urls: UnsplashUrls?
)

@Serializable
data class UnsplashUrls(
    val raw: String?,
    val full: String?,
    val regular: String?,
    val small: String?,
    val thumb: String?
)
