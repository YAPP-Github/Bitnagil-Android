package com.threegap.bitnagil.presentation.emotion.model

import android.os.Parcelable
import com.threegap.bitnagil.domain.emotion.model.EmotionRecommendRoutine
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmotionRecommendRoutineUiModel(
    val id: String,
    val name: String,
    val description: String,
    val selected: Boolean,
) : Parcelable {
    companion object {
        fun fromEmotionRecommendRoutine(
            emotionRecommendRoutine: EmotionRecommendRoutine,
        ): EmotionRecommendRoutineUiModel {
            return EmotionRecommendRoutineUiModel(
                id = emotionRecommendRoutine.routineId,
                name = emotionRecommendRoutine.routineName,
                description = emotionRecommendRoutine.routineDescription,
                selected = false,
            )
        }
    }
}
