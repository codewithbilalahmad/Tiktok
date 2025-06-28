package com.muhammad.core

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class DisableRippleInteractionSource : MutableInteractionSource {
    override suspend fun emit(interaction: Interaction) {
    }

    override fun tryEmit(interaction: Interaction): Boolean = true

    override val interactions: Flow<Interaction>
        get() = emptyFlow()
}