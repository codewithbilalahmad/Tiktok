package com.muhammad.data.domain.repository.templates

import com.muhammad.core.DataError
import com.muhammad.core.Result
import com.muhammad.data.remote.templates.dto.UnsplashSearchResponse

interface TemplateRepository {
    suspend fun getTemplates(page : Int) : Result<UnsplashSearchResponse, DataError.Network>
}