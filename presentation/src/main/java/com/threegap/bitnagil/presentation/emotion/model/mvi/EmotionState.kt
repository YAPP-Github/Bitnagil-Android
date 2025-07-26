package com.threegap.bitnagil.presentation.emotion.model.mvi

import androidx.compose.runtime.Immutable
import com.threegap.bitnagil.presentation.common.mviviewmodel.MviState
import com.threegap.bitnagil.presentation.emotion.model.Emotion
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class EmotionState(
    val emotions: List<Emotion>,
    val isLoading: Boolean,
) : MviState {
    companion object {
        val Init = EmotionState(
            emotions = emptyList(),
            isLoading = true,
        )
    }
}
