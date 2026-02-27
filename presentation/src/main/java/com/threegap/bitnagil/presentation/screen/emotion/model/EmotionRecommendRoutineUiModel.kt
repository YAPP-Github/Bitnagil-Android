package com.threegap.bitnagil.presentation.screen.emotion.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmotionRecommendRoutineUiModel(
    val id: String,
    val name: String,
    val description: String,
    val selected: Boolean,
) : Parcelable

internal fun EmotionRecommendRoutine.toUiModel(): EmotionRecommendRoutineUiModel =
    EmotionRecommendRoutineUiModel(
        id = this.routineId,
        name = this.routineName,
        description = this.routineDescription,
        selected = false,
    )
