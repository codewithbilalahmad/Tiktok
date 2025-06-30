package com.muhammad.feature.loginwithemailphone

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnPageChange -> {
                _state.update { it.copy(settledPage = event.settledPage) }
            }
        }
    }
}