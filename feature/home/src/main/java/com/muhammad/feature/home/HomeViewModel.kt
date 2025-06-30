package com.muhammad.feature.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.core.Result
import com.muhammad.core.asUiText
import com.muhammad.data.domain.repository.tiktok.TiktokRepository
import com.muhammad.data.remote.tiktok.mapper.comments.toComment
import com.muhammad.data.remote.tiktok.mapper.shorts.toVideo
import com.muhammad.data.remote.tiktok.mapper.trending.toVideo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val context: Context,
    private val tiktokRepository: TiktokRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        loadShortVideos(page = state.value.videoPage, isPaginate = false)
        loadTrendingShortVideos(page = state.value.trendingVideoPage, isPaginate = false)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnPaginateShortVideos -> {
                loadShortVideos(page = state.value.videoPage + 1, isPaginate = true)
            }

            HomeEvent.OnPaginateTrendingShortVideos -> {
                _state.update { it.copy(trendingVideoPage = state.value.trendingVideoPage + 1) }
                loadTrendingShortVideos(page = state.value.trendingVideoPage, isPaginate = true)
            }

            is HomeEvent.OnCommentChange -> {
                _state.update { it.copy(comment = event.comment) }
            }

            is HomeEvent.OnCommentClick -> {
                _state.update { it.copy(showCommentBottomSheet = !state.value.showCommentBottomSheet) }
            }

            HomeEvent.OnToggleCommentBottomSheet -> {
                _state.update { it.copy(showCommentBottomSheet = !state.value.showCommentBottomSheet) }
            }

            HomeEvent.OnPaginateComments -> {
                _state.update { it.copy(commentPage = state.value.commentPage + 1) }
                loadVideoComments(
                    videoId = state.value.selectedVideoId,
                    page = state.value.commentPage,
                    isPaginate = true
                )
            }

            is HomeEvent.LoadComments -> {
                loadVideoComments(
                    videoId = event.videoId,
                    page = state.value.commentPage,
                    isPaginate = false
                )
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

    private fun loadVideoComments(videoId: String, page: Int, isPaginate: Boolean = false) {
        if (state.value.selectedVideoId == videoId && !isPaginate) {
            _state.update { it.copy(comments = state.value.comments, isCommentsLoading = false) }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(selectedVideoId = videoId) }
                _state.update {
                    if (isPaginate) it.copy(isMoreCommentsLoading = true) else it.copy(
                        isCommentsLoading = true
                    )
                }
                val result = tiktokRepository.getComments(videoId = videoId, page = page)
                when (result) {
                    is Result.Failure -> {
                        _state.update {
                            if (isPaginate) it.copy(isMoreCommentsLoading = false) else it.copy(
                                isCommentsLoading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        val newComments = result.data.items?.map { it.toComment() }.orEmpty()
                        _state.update {
                            if (isPaginate) it.copy(isMoreCommentsLoading = false) else it.copy(
                                isCommentsLoading = false
                            )
                        }
                        _state.update {
                            if (isPaginate) it.copy(comments = it.comments + newComments) else it.copy(
                                comments = newComments
                            )
                        }
                    }
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
            val result = tiktokRepository.getTrendingShortVideos(page)
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
                    val newVideos = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        if (isPaginate) it.copy(
                            isMoreTrendingShortVideosLoading = false,
                        ) else it.copy(
                            isTrendingShortVideosLoading = false,
                        )
                    }
                    _state.update {
                        if (isPaginate) it.copy(trendingVideos = it.trendingVideos + newVideos) else it.copy(
                            trendingVideos = newVideos
                        )
                    }
                }
            }
        }
    }
}