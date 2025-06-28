package com.muhammad.feature.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.core.Result
import com.muhammad.core.asUiText
import com.muhammad.data.domain.repository.tiktok.TiktokRepository
import com.muhammad.data.remote.tiktok.mapper.shorts.toVideo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val context : Context,
    private val tiktokRepository: TiktokRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()
    init {
        loadShortVideos(page=  state.value.videoPage,isPaginate = false)
        loadTrendingShortVideos(page=  state.value.trendingVideoPage,isPaginate = false)
    }
    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnPaginateShortVideos -> {
                loadShortVideos(page = state.value.videoPage +1,isPaginate = true)
            }

            HomeEvent.OnPaginateTrendingShortVideos -> {
                loadTrendingShortVideos(page = state.value.trendingVideoPage +1,isPaginate = true)
            }
        }
    }

    private fun loadShortVideos(page: Int, isPaginate: Boolean = false) {
        viewModelScope.launch {
            _state.update {
                if (isPaginate) it.copy(isMoreShortVideosLoading = true) else it.copy(
                    isShortVideosLoading = true
                )
            }
            val result = tiktokRepository.getShortVideos(page)
            when (result) {
                is Result.Failure -> {
                    _state.update {
                        if (isPaginate) it.copy(
                            isMoreShortVideosLoading = false,
                            shortVideoError = result.error.asUiText().asString(context)
                        ) else it.copy(
                            isShortVideosLoading = false,
                            trendingShortVideoError = result.error.asUiText().asString(context)
                        )
                    }
                }

                is Result.Success -> {
                    val newVideo = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        if (isPaginate) it.copy(
                            isMoreShortVideosLoading = false
                        ) else it.copy(isShortVideosLoading = false)
                    }
                    _state.update { it.copy(shortVideos = it.shortVideos + newVideo) }
                }
            }
        }
    }
    private fun loadTrendingShortVideos(page: Int, isPaginate: Boolean = false) {
        viewModelScope.launch {
            _state.update {
                if (isPaginate) it.copy(isMoreTrendingShortVideosLoading = true) else it.copy(
                    isTrendingShortVideosLoading = true
                )
            }
            val result = tiktokRepository.getShortVideos(page)
            when (result) {
                is Result.Failure -> {
                    _state.update {
                        if (isPaginate) it.copy(
                            isMoreTrendingShortVideosLoading = false,
                            shortVideoError = result.error.asUiText().asString(context)
                        ) else it.copy(
                            isTrendingShortVideosLoading = false,
                            trendingShortVideoError = result.error.asUiText().asString(context)
                        )
                    }
                }

                is Result.Success -> {
                    val newVideo = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        if (isPaginate) it.copy(
                            isMoreTrendingShortVideosLoading = false
                        ) else it.copy(isTrendingShortVideosLoading = false)
                    }
                    _state.update { it.copy(trendingVideos = it.trendingVideos + newVideo) }
                }
            }
        }
    }
}