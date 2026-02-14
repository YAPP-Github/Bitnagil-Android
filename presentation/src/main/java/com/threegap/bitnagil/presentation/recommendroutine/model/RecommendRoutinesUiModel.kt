package com.threegap.bitnagil.presentation.recommendroutine.model

import com.threegap.bitnagil.domain.emotion.model.EmotionMarbleType
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendCategory
import com.threegap.bitnagil.domain.recommendroutine.model.RecommendRoutines

data class RecommendRoutinesUiModel(
    val recommendRoutinesByCategory: Map<RecommendCategory, List<RecommendRoutineUiModel>>,
    val emotionMarbleType: EmotionMarbleType?,
) {
    companion object {
        val INIT = RecommendRoutinesUiModel(
            recommendRoutinesByCategory = emptyMap(),
            emotionMarbleType = null,
        )
    }
}

internal fun RecommendRoutines.toUiModel(): RecommendRoutinesUiModel =
    RecommendRoutinesUiModel(
        recommendRoutinesByCategory = this.recommendRoutinesByCategory.mapValues { (_, routines) ->
            routines.map { it.toUiModel() }
        },
        emotionMarbleType = this.emotionMarbleType,
    )
