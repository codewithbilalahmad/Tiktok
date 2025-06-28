package com.muhammad.data.remote.templates.repository

import com.muhammad.core.Constants.UNSPLASH_API_KEY
import com.muhammad.core.Constants.UNSPLASH_BASE_URL
import com.muhammad.core.DataError
import com.muhammad.core.Result
import com.muhammad.core.get
import com.muhammad.data.domain.repository.templates.TemplateRepository
import com.muhammad.data.remote.templates.dto.UnsplashSearchResponse
import io.ktor.client.HttpClient

class TemplateRepositoryImp(
    private val httpClient : HttpClient
) : TemplateRepository {
    override suspend fun getTemplates(page : Int): Result<UnsplashSearchResponse, DataError.Network> {
        return httpClient.get(
            route = "${UNSPLASH_BASE_URL}search/photos",
            queryParameters = mapOf(
                "query" to "templates",
                "per_page" to "20",
                "page" to page,
                "client_id" to UNSPLASH_API_KEY
            )
        )
    }
}