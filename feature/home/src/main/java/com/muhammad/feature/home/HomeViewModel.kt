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
        loadShortVideos(page = state.value.videoPage)
        loadTrendingShortVideos(page = state.value.trendingVideoPage)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnPaginateShortVideos -> {
                loadMoreShortVideos()
            }

            HomeEvent.OnPaginateTrendingShortVideos -> {
                loadMoreTrendingShortVideos()
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
                loadMoreVideoComments()
            }

            is HomeEvent.LoadComments -> {
                loadVideoComments(
                    videoId = event.videoId,
                    page = state.value.commentPage,
                )
            }
        }
    }

    private fun loadShortVideos(page: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isShortVideosLoading = true
                )
            }
            val result = tiktokRepository.getShortVideos(page)
            when (result) {
                is Result.Failure -> {
                    _state.update {
                        it.copy(
                            isShortVideosLoading = false,
                            shortVideoError = result.error.asUiText().asString(context)
                        )
                    }
                }

                is Result.Success -> {
                    val newVideo = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        it.copy(
                            isShortVideosLoading = false, shortVideos = newVideo
                        )
                    }
                }
            }
        }
    }

    private fun loadMoreShortVideos() {
        viewModelScope.launch {
            if (state.value.isMoreShortVideosLoading) return@launch
            val nextPage = state.value.videoPage + 1
            _state.update {
                it.copy(
                    isMoreShortVideosLoading = true, videoPage = nextPage
                )
            }
            val result = tiktokRepository.getShortVideos(nextPage)
            when (result) {
                is Result.Failure -> {
                    val previousPage = state.value.videoPage - 1
                    _state.update {
                        it.copy(
                            isShortVideosLoading = false,
                            videoPage = previousPage,
                            shortVideoError = result.error.asUiText().asString(context)
                        )
                    }
                }

                is Result.Success -> {
                    val newVideo = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        it.copy(
                            isShortVideosLoading = true,
                            shortVideos = state.value.shortVideos + newVideo
                        )
                    }
                }
            }
        }
    }

    private fun loadVideoComments(videoId: String, page: Int) {
        if (state.value.selectedVideoId == videoId) {
            _state.update { it.copy(comments = state.value.comments, isCommentsLoading = false) }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(selectedVideoId = videoId) }
                _state.update {
                    it.copy(
                        isCommentsLoading = true
                    )
                }
                val result = tiktokRepository.getComments(videoId = videoId, page = page)
                when (result) {
                    is Result.Failure -> {
                        _state.update {
                            it.copy(
                                isCommentsLoading = false
                            )
                        }
                    }

                    is Result.Success -> {
                        val newComments = result.data.items?.map { it.toComment() }.orEmpty()
                        _state.update {
                            it.copy(
                                isCommentsLoading = false
                            )
                        }
                        _state.update {
                            it.copy(
                                comments = newComments
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadMoreVideoComments() {
        viewModelScope.launch {
            if (state.value.isMoreCommentsLoading) return@launch
            val nextPage = state.value.commentPage + 1
            _state.update {
                it.copy(
                    isMoreCommentsLoading = true, commentPage = nextPage
                )
            }
            val result =
                tiktokRepository.getComments(videoId = state.value.selectedVideoId, page = nextPage)
            when (result) {
                is Result.Failure -> {
                    val previousPage = state.value.commentPage - 1
                    _state.update {
                        it.copy(
                            isMoreCommentsLoading = false, commentPage = previousPage
                        )
                    }
                }

                is Result.Success -> {
                    val newComments = result.data.items?.map { it.toComment() }.orEmpty()
                    _state.update {
                        it.copy(
                            isMoreCommentsLoading = false,
                            comments = state.value.comments + newComments
                        )
                    }
                }
            }
        }
    }

    private fun loadTrendingShortVideos(page: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isTrendingShortVideosLoading = true
                )
            }
            val result = tiktokRepository.getTrendingShortVideos(page)
            when (result) {
                is Result.Failure -> {
                    _state.update {
                        it.copy(
                            isTrendingShortVideosLoading = false,
                            trendingShortVideoError = result.error.asUiText().asString(context)
                        )
                    }
                }

                is Result.Success -> {
                    val newVideos = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        it.copy(
                            isTrendingShortVideosLoading = false, trendingVideos = newVideos
                        )
                    }
                }
            }
        }
    }

    private fun loadMoreTrendingShortVideos() {
        viewModelScope.launch {
            if (state.value.isMoreTrendingShortVideosLoading) return@launch
            val nextPage = state.value.trendingVideoPage + 1
            _state.update {
                it.copy(
                    isMoreTrendingShortVideosLoading = true, trendingVideoPage = nextPage
                )
            }
            val result = tiktokRepository.getTrendingShortVideos(nextPage)
            when (result) {
                is Result.Failure -> {
                    val previousPage = state.value.trendingVideoPage -1
                    _state.update {
                        it.copy(
                            isMoreTrendingShortVideosLoading = false,
                            trendingVideoPage = previousPage,
                            trendingShortVideoError = result.error.asUiText().asString(context)
                        )
                    }
                }

                is Result.Success -> {
                    val newVideos = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        it.copy(
                            isTrendingShortVideosLoading = false,
                            trendingVideos = state.value.trendingVideos + newVideos
                        )
                    }
                }
            }
        }
    }
}