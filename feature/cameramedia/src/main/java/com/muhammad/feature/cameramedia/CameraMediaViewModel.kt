package com.muhammad.feature.cameramedia

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.core.Result
import com.muhammad.data.domain.repository.templates.TemplateRepository
import com.muhammad.data.remote.templates.mapper.toTemplate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CameraMediaViewModel(
    private val templateRepository: TemplateRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(CameraMediaState())
    val state = _state.asStateFlow()
    init {
        onEvent(CameraMediaEvent.FetchTemplates)
    }
    fun onEvent(event: CameraMediaEvent) {
        when (event) {
            CameraMediaEvent.FetchTemplates -> {
                loadTemplates()
            }
        }
    }
    private fun loadTemplates() {
        viewModelScope.launch {
            _state.update { it.copy(isTemplatesLoading = true) }
            val result = templateRepository.getTemplates(page = state.value.templatePage)
            when (result) {
                is Result.Failure -> {
                    _state.update { it.copy(isTemplatesLoading = false) }
                }

                is Result.Success -> {
                    val response = result.data.results.map { it.toTemplate() }
                    _state.update { it.copy(isTemplatesLoading = false) }
                    _state.update { it.copy(templates = response) }
                }
            }
        }
    }
}