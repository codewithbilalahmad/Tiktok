package com.muhammad.data.domain.repository.tiktok

import com.muhammad.core.DataError
import com.muhammad.core.Result
import com.muhammad.data.remote.tiktok.dto.comments.CommentsDto
import com.muhammad.data.remote.tiktok.dto.createrProfile.CreatorProfileDto
import com.muhammad.data.remote.tiktok.dto.shorts.ShortVideosDto

interface TiktokRepository {
    suspend fun getShortVideos(page: Int): Result<ShortVideosDto, DataError.Network>
    suspend fun getTrendingShortVideos(page : Int) : Result<ShortVideosDto, DataError.Network>
    suspend fun getComments(
        videoId: String,
        limit: Int = 10,
        page: Int,
    ): Result<CommentsDto, DataError.Network>

    suspend fun getCreatorProfile(
        id: String,
    ): Result<CreatorProfileDto, DataError.Network>
}