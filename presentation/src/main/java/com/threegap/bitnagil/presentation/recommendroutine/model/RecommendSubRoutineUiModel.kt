package com.threegap.bitnagil.presentation.recommendroutine.model

import com.threegap.bitnagil.domain.recommendroutine.model.RecommendSubRoutine

data class RecommendSubRoutineUiModel(
    val id: Long,
    val name: String,
)

internal fun RecommendSubRoutine.toUiModel(): RecommendSubRoutineUiModel =
    RecommendSubRoutineUiModel(
        id = this.id,
        name = this.name,
    )
