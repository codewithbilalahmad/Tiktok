package com.muhammad.feature.creatorprofile

import androidx.lifecycle.*
import com.muhammad.core.Result
import com.muhammad.data.domain.repository.tiktok.TiktokRepository
import com.muhammad.data.remote.tiktok.mapper.creatorProfile.toUser
import com.muhammad.data.remote.tiktok.mapper.shorts.toVideo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.collections.mapNotNull

class CreatorProfileViewModel(
    savedStateHandle: SavedStateHandle,
    private val tiktokRepository: TiktokRepository,
) : ViewModel() {
    private val userId = savedStateHandle.get<String>("userId")
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        onEvent(ProfileEvent.LoadUserData)
        onEvent(ProfileEvent.LoadUserShortVideos)
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadUserData -> loadUserData()
            ProfileEvent.LoadUserShortVideos -> loadUserShortVideos()
            ProfileEvent.PaginateUserShortVideos -> paginateUserShortVideos()
            is ProfileEvent.OnProfileVideoClick -> {
                _state.update { it.copy(selectedProfileVideosIndex = event.index) }
            }
            ProfileEvent.ToggleProfileVideoSection -> {
                _state.update { it.copy(showProfileVideosSection = !state.value.showProfileVideosSection) }
            }
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = tiktokRepository.getCreatorProfile(userId ?: return@launch)
            when (result) {
                is Result.Failure -> {
                    _state.update { it.copy(isLoading = false) }
                }

                is Result.Success -> {
                    val response = result.data.items.firstOrNull()?.toUser()
                    _state.update { it.copy(isLoading = false, user = response) }
                }
            }
        }
    }

    private fun loadUserShortVideos() {
        viewModelScope.launch {
            _state.update { it.copy(isUserShortVideoLoading = true) }
            val result = tiktokRepository.getProfileShortVideos(
                page = state.value.userVideosPage,
                userId = userId ?: return@launch
            )
            when (result) {
                is Result.Failure -> {
                    _state.update { it.copy(isUserShortVideoLoading = false) }
                }

                is Result.Success -> {
                    val response = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        it.copy(
                            isUserShortVideoLoading = false,
                            userShortVideos = response
                        )
                    }
                }
            }
        }
    }

    private fun paginateUserShortVideos() {
        viewModelScope.launch {
            if(state.value.isMoreUserShortVideoLoading) return@launch
            val nextPage = state.value.userVideosPage + 1
            _state.update {
                it.copy(
                    isMoreUserShortVideoLoading = true,
                    userVideosPage = nextPage
                )
            }
            val result = tiktokRepository.getProfileShortVideos(
                page = nextPage,
                userId = userId ?: return@launch
            )
            when (result) {
                is Result.Failure -> {
                    val previousPage =  state.value.userVideosPage - 1
                    _state.update { it.copy(isMoreUserShortVideoLoading = false, userVideosPage = previousPage) }
                }

                is Result.Success -> {
                    val response = result.data.items?.mapNotNull { it?.toVideo() }.orEmpty()
                    _state.update {
                        it.copy(
                            isMoreUserShortVideoLoading = false,
                            userShortVideos = state.value.userShortVideos + response
                        )
                    }
                }
            }
        }
    }
}