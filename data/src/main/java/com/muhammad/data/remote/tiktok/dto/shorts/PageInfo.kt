package com.muhammad.data.remote.tiktok.dto.shorts


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageInfo(
    @SerialName("resultsPerPage")
    val resultsPerPage: Int?,
    @SerialName("totalResults")
    val totalResults: Int?
)