package com.muhammad.data.remote.tiktok.repository

import com.muhammad.core.Constants.TIKTOK_API_KEY
import com.muhammad.core.Constants.TIKTOK_BASE_URL
import com.muhammad.core.DataError
import com.muhammad.core.Result
import com.muhammad.core.get
import com.muhammad.data.domain.repository.tiktok.TiktokRepository
import com.muhammad.data.remote.tiktok.dto.comments.CommentsDto
import com.muhammad.data.remote.tiktok.dto.createrProfile.CreatorProfileDto
import com.muhammad.data.remote.tiktok.dto.shorts.ShortVideosDto
import com.muhammad.data.remote.tiktok.dto.trending.TrendingShortVideosDto
import io.ktor.client.HttpClient

class TikTokRepositoryImp(
    private val httpClient : HttpClient
) : TiktokRepository {
    override suspend fun getTrendingShortVideos(page: Int): Result<TrendingShortVideosDto, DataError.Network> {
        return httpClient.get<TrendingShortVideosDto>(route = "${TIKTOK_BASE_URL}videos", queryParameters = mapOf(
            "part" to "snippet,statistics,contentDetails",
            "key" to TIKTOK_API_KEY,
            "chart" to "mostPopular",
            "maxResults" to 10,
            "page" to page
        ))
    }
    override suspend fun getComments(
        videoId: String,
        limit: Int,
        page: Int
    ): Result<CommentsDto, DataError.Network> {
        return httpClient.get<CommentsDto>(route = "${TIKTOK_BASE_URL}commentThreads", queryParameters = mapOf(
            "part" to "snippet",
            "videoId" to videoId,
            "maxResults" to limit,
            "key" to TIKTOK_API_KEY,
            "page" to page
        ))
    }

    override suspend fun getCreatorProfile(id: String): Result<CreatorProfileDto, DataError.Network> {
        return httpClient.get<CreatorProfileDto>(route = "${TIKTOK_BASE_URL}channels", queryParameters = mapOf(
            "part" to "snippet,statistics",
            "id" to id,
            "key" to TIKTOK_API_KEY,
        ))
    }

    override suspend fun getShortVideos(page: Int): Result<ShortVideosDto, DataError.Network> {
        return httpClient.get<ShortVideosDto>(route = "${TIKTOK_BASE_URL}search", queryParameters = mapOf(
            "part" to "snippet",
            "q" to "shorts",
            "type" to "video",
            "videoDuration" to "short",
            "maxResults" to 10,
            "key" to TIKTOK_API_KEY,
            "page" to page
        ))
    }

    override suspend fun getProfileShortVideos(page: Int, userId : String): Result<ShortVideosDto, DataError.Network> {
        return httpClient.get<ShortVideosDto>(route = "${TIKTOK_BASE_URL}search", queryParameters = mapOf(
            "part" to "snippet",
            "channelId" to userId,
            "order" to "date",
            "type" to "video",
            "maxResults" to 15,
            "key" to TIKTOK_API_KEY,
            "page" to page
        ))
    }
}